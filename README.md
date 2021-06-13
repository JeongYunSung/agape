# 아가페 서비스

## 아키텍처

                     CORE 서버1
API 게이트웨이서버 -> CORE 서버2 -> MariaDB
                     CORE 서버3
                     

위와같이 게이트웨이 서버를 통해 로드밸런싱 구축 ( 방식은 라운드로빈 )

DB는 싱글구조입니다

## docker

* docker-compose -f docker-compose-mariadb.yml up -d

## url

* http://localhost:8080/signIn

* http://localhost:8080/signUp

* http://localhost:8080/profile

* http://localhost:8080/orders

* http://localhost:8080/markets

* others...
