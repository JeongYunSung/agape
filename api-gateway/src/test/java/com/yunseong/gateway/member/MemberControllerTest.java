package com.yunseong.gateway.member;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class MemberControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private MemberController memberController;

    @CsvSource({"id1, pw1, na1", "id2, pw2, na2", "id3, pw3, na3"})
    @ParameterizedTest
    void 성공적으로_회원가입(long id, String em, String pw, String na) {
//        // Arrange
//        CreateMemberRequest request = new CreateMemberRequest(em, pw, na);
//        CreateMemberResponse response = new CreateMemberResponse(id, em, na, LocalDateTime.now());
//        when(this.memberController.signUp(request)).thenReturn(ResponseEntity.created(URI.create("/" + id)).body(response));
//        // Act
//        var actual = this.webTestClient
//                .method(HttpMethod.POST)
//                .uri("/signUp")
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(BodyInserters.fromValue(request))
//                .exchange();
//        // Assert
//        actual
//                .expectStatus()
//                .isCreated()
//                .expectBody(CreateMemberResponse.class);
    }
}