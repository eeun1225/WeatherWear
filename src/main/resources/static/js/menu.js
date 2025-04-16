function toggleMenu() {
    const sideMenu = document.getElementById("mySidenav");
    const overlay = document.getElementById("overlay");

    if (sideMenu.classList.contains("active")) {
        closeNav();
    } else {
        sideMenu.classList.add("active");
        overlay.style.display = "block"; // 배경 오버레이 표시
    }
}

function closeNav() {
    document.getElementById("mySidenav").classList.remove("active");
    document.getElementById("overlay").style.display = "none"; // 배경 오버레이 숨김
}

// 햄버거 아이콘 클릭 이벤트 추가
document.getElementById("menuIcon").addEventListener("click", function (event) {
    event.stopPropagation(); // 클릭 이벤트 버블링 방지
    toggleMenu();
});

// X 버튼 클릭 시 닫기
document.getElementById("closeBtn").addEventListener("click", closeNav);
