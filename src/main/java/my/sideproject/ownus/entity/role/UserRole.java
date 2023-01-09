package my.sideproject.ownus.entity.role;

import lombok.Getter;

@Getter
public enum UserRole {

    //spring security가 제공하는 Role 네이밍 정책이 <Role_권한>이므로 맞춰서 작성해준다.
    ROLE_ADMIN("관리자"),
    ROLE_USER("일반사용자");

    private String description;

    UserRole(String description) {
        this.description = description;
    }
}
