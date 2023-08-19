DailyNews 앱
DailyNews 로고

"DailyNews" 앱은 날씨, 뉴스, 알람, 할일 목록 기능을 제공하는 다기능 앱입니다. 아래는 각 기능별로 제공되는 내용을 소개합니다.

기능 소개

1. 날씨
   날씨 기능은 사용자의 현재 위치를 기반으로 한 날씨 정보를 표시합니다.
   사용자는 현재 날씨, 기온, 습도, 3일간의 예보 등의 정보를 확인할 수 있습니다.

2. 뉴스
   뉴스 기능은 주요 뉴스 헤드라인과 짧은 내용을 제공합니다.
   DailyNews 내부 브라우저나 외부 인터넷 앱을 통해 자세한 기사를 확인할 수 있습니다.
   사용자는 최근 뉴스를 탐색하고, 상세 내용을 확인할 수 있습니다.

3. 알람
   알람 기능은 사용자가 원하는 시간에 알람을 설정할 수 있는 기능을 제공합니다.
   알람이 울릴 시간과 제목을 설정할 수 있으며, 설정한 시간이 되면 알림이 표시됩니다.

4. 할일 목록
   할일 목록 기능은 사용자의 일정을 관리하는 기능을 제공합니다.
   사용자는 할일을 추가하고 완료 여부를 체크하여 일정을 관리할 수 있습니다.

설치 및 사용 방법

DailyNews 앱을 다운로드하거나 APK 파일을 설치합니다.
앱을 실행하면 Bottom Navigation이 표시됩니다.
Bottom Navigation의 각 탭을 눌러 원하는 기능으로 이동합니다.
각 기능 화면에서 해당 기능을 사용하고 정보를 확인하거나 설정할 수 있습니다.

개발 환경

Android Studio 2022.2.1
프로그래밍 언어 Kotlin
최소 API 레벨: 26 이상
권장 API 레벨: 32 이상

라이브러리

Retrofit(2.9.0)
Retrofit은 HTTP 통신을 위한 라이브러리로, 날씨와 뉴스 데이터를 서버로부터 가져오기 위해 사용했습니다.
간편한 API 호출 및 JSON 데이터 변환을 지원하며, 안정적이고 효율적인 통신을 구현할 수 있습니다.

GitHub: square/retrofit

Glide(4.13.2)
Glide는 이미지 로딩과 캐싱 라이브러리로, 날씨 정보의 아이콘 이미지를 표시하기 위해 사용했습니다.
자동적인 이미지 리사이징 및 로딩 최적화를 지원하며, 부드러운 이미지 로딩 경험을 제공합니다.

GitHub: bumptech/glide

Material Design Components(1.6.0)
Material Design Components는 구글이 제공하는 디자인 라이브러리로, UI 디자인의 일관성과 시각적 품질을 향상시키기 위해 사용했습니다.
버튼, 카드, 텍스트 필드 등 다양한 UI 컴포넌트를 포함하고 있습니다.

GitHub: material-components/material-components-android

Koin(3.3.0): 의존성 주입(Dependency Injection)을 위한 경량화된 프레임워크로, 앱의 컴포넌트 간 의존성을 관리합니다.

Kotlin Coroutines(1.6.1): Kotlin의 비동기 프로그래밍 라이브러리로, 비동기 작업을 더 효율적으로 다룰 수 있게 해줍니다.

Gson(2.9.0): JSON 데이터와 Kotlin 객체 간의 변환을 지원하는 라이브러리입니다.

Lottie(4.2.2): 애니메이션을 JSON 형식으로 정의하고 재생하는 라이브러리입니다.

lifecycle (2.4.1): 안드로이드 앱의 라이프사이클을 관리하는 컴포넌트 라이브러리입니다.

디버깅
leakcanary (2.9.1): 앱의 메모리 누수를 감지하고 분석하는 라이브러리입니다.
chucker (3.5.2): HTTP 요청 및 응답을 디버깅하고 로깅하는 라이브러리입니다.

버그 보고
앱 사용 중 발견한 버그나 이슈를 github 에서 보고해주세요.