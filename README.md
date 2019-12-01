Project - Reactive Blog
===

> 리액티브 프로그래밍의 학습을 위해서 `Spring-WebFlux`, `Mono/Flux`, `Reactive-Monogo`를 이용하여 진행하는 비동기 Blog API 프로젝트. 

## 개발환경

|도구|버전|
|:---:|:---:|
| Framework |Spring Boot 2.2.1 |
| OS |Windows 10, Ubuntu 18.04|
| IDE |IntelliJ IDEA Ultimate |
| JDK |JDK 1.8+|
| DataBase |MongoDB Server 4.2.1|
| Build Tool | Gradle 4.8.1 |

## 개발 방법
<details><summary>세부정보</summary>

* 개발과 관련된 모든 이야기는 [Issues](https://github.com/donghL-dev/Reactive-Blog/issues)에서 진행합니다.

* API 및 도메인 명세를 기반으로 개발을 진행하며, 명세에 변경사항이 생길 경우 빠른 시일내에 최신화 합니다.

* **Fork**를 통한 PR을 지향합니다.

* 아래와 같은 Git Workflow를 지향하며 지키려고 노력합니다. ([참고](https://nvie.com/posts/a-successful-git-branching-model/?))

    <img width=750, height=850, src="https://camo.githubusercontent.com/7f2539ff6001fe7700853313e7cdb7fd4602e16a/68747470733a2f2f6e7669652e636f6d2f696d672f6769742d6d6f64656c4032782e706e67">

</details>

## 실행 방법
<details><summary>세부정보</summary>

* 준비사항.

    * Gradle or IntelliJ IDEA

    * JDK (>= 1.8)

    * Spring Boot (>= 2.x)

* 저장소를 `clone`

    ```bash
    $ git clone https://github.com/donghL-dev/Reactive-Blog.git
    ```

* 데이터 베이스는 MongoDB를 사용해야 합니다.

* 프로젝트 내 Reactive-Blog\src\main\resources 경로에 `application.yml` 생성.

    * 밑의 양식대로 내용을 채운 뒤, `application.yml`에 삽입.
    <br>

    ```yml
    spring:
        data:
            mongodb:
                host: # 본인의 DB 서버 주소를 넣으면 되는데, 왠만하면 localhost입니다.
                port: # 본인의 DB 서버 PORT 왠만하면 27017입니다.
                database: # 본인의 데이터베이스 이름을 기재하시면 됩니다.
    ```

* IntelliJ IDEA(>= 2018.3)에서 해당 프로젝트를 `Open`

    * 또는 터미널을 열어서 프로젝트 경로에 진입해서 다음 명령어를 실행.

    * Windows 10

        ```bash
        $ gradlew bootRun
        ```

    * Ubuntu 18.04

        ```
        $ ./gradlew bootRun
        ```

</details>

## 배포 서버

* 추후 추가 예정

## Dependencies

* `Spring Reactive Web`

* `Spring Data Reactive MongoDB`

* `Embedded MongoDB Database`

* `Spring Security`

* `Lombok`

## 도메인 명세

* [도메인 명세 문서](https://www.notion.so/dhlab/52ff6bb691934fbabeca5287bc32dffb)

## API 명세 

* 추후 추가 예정.