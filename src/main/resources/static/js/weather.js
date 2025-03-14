import ERROR_MESSAGES from './errorMessages.js';

// 페이지 로드 시 실행(자동 실행)
window.onload = function() {
    console.log("✅ window.onload 실행됨!");  //
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

// 날씨 데이터 요청
function fetchWeather(lat, lon) {
    const { token, header } = getCsrfToken();

    fetch('/weather', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [header]: token // CSRF 토큰 추가
        },
        body: JSON.stringify({ latitude: lat, longitude: lon })
    })
        .then(response => response.json())
        .then(data => updateWeatherUI(data))
        .catch(() => ErrorMessage("WEATHER_ERROR"));
}

// UI 업데이트
function updateWeatherUI(data) {
    if (!data) {
        ErrorMessage("WEATHER_ERROR");
        return;
    }

    // 메인 화면 UI 업데이트
    document.getElementById('weatherBox').innerHTML = `
        <div class="weather-icon">${data.weatherIcon || '☀️'}</div>
        <div class="weather-info"><strong>현재 기온:</strong> ${data.temperature}℃</div>
    `;

    // 웨더 화면 UI 업데이트 (상세 정보)
    document.getElementById('detailWeather').innerHTML = `
        <div class="weather-info"><strong>현재 위치:</strong> ${data.areaRequest.step1} > ${data.areaRequest.step2} > ${data.areaRequest.step3}</div>
        <div class="weather-icon">${data.weatherIcon || '☀️'}</div>
        <div class="weather-info"><strong>현재 기온:</strong> ${data.temperature}℃</div>
        <div class="weather-info"><strong>풍속:</strong> ${data.windSpeed}m/s</div>
        <div class="weather-info"><strong>강수량:</strong> ${data.precipitation}mm</div>
        <div class="weather-info"><strong>습도:</strong> ${data.humidity}%</div>
    `;
}
