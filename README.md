# 개발환경
1. IDE: IntelliJ IDEA Community
2. Spring Boot 2.6.13
3. JDK 11
4. mysql
5. Spring Data JPA
6. Thymeleaf

# 다이어리의 기본기능
1. 일기를 적으면 날짜와 유형, 일기의 내용을 기록해줌
2. 오늘 저장한 일기만 보임. 같은 페이지 한칸 띄고 일기의 형태로 저장됨.

# 다이어리 주요기능
1. 일기 쓰기(/board/save)
   - 일기 내용과 날짜를 저장한다.
2. 일기 목록(/board/)
   - 게시글이 아니라 날짜가 목록
3. 일기 조회(/board/{date})
   - id 대신 date를 저장한다.
   - 해당 날짜의 일기들을 모두 보여주기
4. 글수정(/board/update/{date})
   