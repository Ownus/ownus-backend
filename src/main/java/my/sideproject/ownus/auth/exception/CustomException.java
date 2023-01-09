package my.sideproject.ownus.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException{
    private ErrorCode errorCode;
}
