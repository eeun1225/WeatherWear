import ERROR_MESSAGES from './errorMessages.js';

// 페이지 로드 시 실행(자동 실행)
window.onload = function () {
    console.log("✅ window.onload 실행됨!");
    getCurrentLocation((lat, lon) => fetchWeather(lat, lon));
};

// 오류메세지 출력
function ErrorMessage(errorType) {
    document.getElementById('weather').innerHTML = `<p class="error-message">${ERROR_MESSAGES[errorType] || ERROR_MESSAGES.GENERIC_ERROR}</p>`;
}

// CSRF 토큰 가져오기
function getCsrfToken() {
    return {
        token: document.querySelector('meta[name="_csrf"]').content,
        header: document.querySelector('meta[name="_csrf_header"]').content
    };
}

// 현재 위치 가져오기
function getCurrentLocation(callback) {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
            (position) => callback(position.coords.latitude, position.coords.longitude),
            () => ErrorMessage("LOCATION_ERROR")
        );
    } else {
        ErrorMessage("LOCATION_ERROR");
    }
}

function fetchWeather(lat, lon) {
    const { token, header } = getCsrfToken();

    // 🌟 sessionStorage에서 기존 데이터 확인
    const cachedWeather = sessionStorage.getItem(`weather_${lat}_${lon}`);
    if (cachedWeather) {
        console.log("✅ 캐시된 날씨 데이터 사용!");
        updateWeatherUI(JSON.parse(cachedWeather));
        return;
    }

    console.log("🔄 새로운 날씨 데이터 요청 중...");

    fetch('/weather', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [header]: token // CSRF 토큰 추가
        },
        body: JSON.stringify({ latitude: lat, longitude: lon })
    })
        .then(response => response.json())
        .then(data => {
            console.log("🌤 받은 날씨 데이터:", data); // ✅ 요거 추가
            sessionStorage.setItem(`weather_${lat}_${lon}`, JSON.stringify(data));
            updateWeatherUI(data);
        })
        .catch(() => ErrorMessage("WEATHER_ERROR"));
}

// UI 업데이트
function updateWeatherUI(data) {
    if (!data) {
        ErrorMessage("WEATHER_ERROR");
        return;
    }

    const { temperature, precipitation, windSpeed, humidity, areaRequest, description, icon } = data;

    // ✅ 메인 페이지 전용
    const weatherBox = document.getElementById('weatherBox');
    if (weatherBox) {
        weatherBox.innerHTML = `
            <div class="weather-icon"><i class="material-icons">${icon}</i></div>
            <div class="weather-info"><strong>${description}</strong></div>
            <div class="weather-info"><strong>현재 기온:</strong> ${temperature}℃</div>
        `;
    }

    // ✅ 날씨 상세 페이지 전용
    const detailWeather = document.getElementById('detailWeather');
    if (detailWeather) {
        detailWeather.innerHTML = `
        <div class="left-panel">
            <div class="location">${areaRequest.step1} ${areaRequest.step2} ${areaRequest.step3}</div>
            <div class="weather-icon"><i class="material-icons">${icon}</i></div>
        </div>
        <div class="right-panel">
            <div class="weather-info"><strong>${description}</strong></div>
            <div class="weather-info"><strong>현재 기온:</strong> ${temperature}℃</div>
            <div class="weather-info"><strong>풍속:</strong> ${windSpeed}m/s</div>
            <div class="weather-info"><strong>강수량:</strong> ${precipitation}mm</div>
            <div class="weather-info"><strong>습도:</strong> ${humidity}%</div>
        </div>
        `;
    }
}
