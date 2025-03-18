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

    // 날씨 아이콘과 설명 가져오기
    const { icon, description } = getWeatherInfo(data.pty, data.humidity, data.temperature);

    // 메인 화면 UI 업데이트
    document.getElementById('weatherBox').innerHTML = `
        <div class="weather-icon"><i class="material-icons">${icon}</i></div>
        <div class="weather-info">${description}</div>
        <div class="weather-info"><strong>현재 기온:</strong> ${data.temperature}℃</div>
    `;

    // 웨더 화면 UI 업데이트 (상세 정보)
    document.getElementById('detailWeather').innerHTML = `
        <div class="weather-info"><strong>현재 위치:</strong> ${data.areaRequest.step1} > ${data.areaRequest.step2} > ${data.areaRequest.step3}</div>
        <div class="weather-icon"><i class="material-icons">${icon}</i></div>
        <div class="weather-info"><strong>${description}</div>
        <div class="weather-info"><strong>현재 기온:</strong> ${data.temperature}℃</div>
        <div class="weather-info"><strong>풍속:</strong> ${data.windSpeed}m/s</div>
        <div class="weather-info"><strong>강수량:</strong> ${data.precipitation}mm</div>
        <div class="weather-info"><strong>습도:</strong> ${data.humidity}%</div>
    `;
}

// 날씨 아이콘 & 설명 매핑
function getWeatherInfo(pty, humidity, temperature) {
    if (pty === 1 || pty === 5) return { icon: "umbrella", description: "비" };
    if (pty === 3 || pty === 7) return { icon: "ac_unit", description: "눈" };
    if (pty === 6) return { icon: "grain", description: "진눈깨비" };

    // PTY = 2 (비 또는 눈) → 온도 기준으로 구분
    if (pty === 2) {
        if (temperature <= 0) return { icon: "ac_unit", description: "눈" };
        if (temperature >= 3) return { icon: "umbrella", description: "비" };
        return { icon: "grain", description: "진눈깨비" };
    }

    // 강수 없음 (PTY = 0) → 습도로 구분
    if (humidity >= 80) return { icon: "cloud_queue", description: "흐림" };
    if (humidity >= 50) return { icon: "cloud", description: "구름 많음" };
    return { icon: "wb_sunny", description: "맑음" };
}


