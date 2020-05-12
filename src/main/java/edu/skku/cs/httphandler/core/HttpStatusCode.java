package edu.skku.cs.httphandler.core;

public interface HttpStatusCode {
    public static final int STATUS_CONTINUE = 100; // 임시 응답, 상태 괜찮으므로 요청 지속
    public static final int STATUS_SWITCHING_PROTOCOL = 101; // 클라이언트의 Upgrade 헤더에 호응
    public static final int STATUS_OK = 200; // 요청 성공
    public static final int STATUS_CREATED = 201; // POST/PUT 요청 성공
    public static final int STATUS_ACCEPTED = 202; // 요청 수신, 호응 불가
    public static final int STATUS_NO_CONTENT = 204; // 요청에 대한 콘텐츠 없음
    public static final int STATUS_MULTIPLE_CHOICE = 300; // 요청에 대한 응답 선택 대기
    public static final int STATUS_MOVED_PERMANENTLY = 301; // 요청한 리소스 URI 영구 변경
    public static final int STATUS_FOUND = 302; // 요청한 리소스 URI 일시 변경
    public static final int STATUS_SEE_OTHER = 303; // 다른 URI 참조 지시
    public static final int STATUS_TEMPORARY_REDIRECT = 307; // 302와 동일하나 추가적으로 메소드 변경 불가 지시
    public static final int STATUS_PERMANENT_REDIRECT = 308; // 301과 동일하나 추가적으로 메소드 변경 불가 지시
    public static final int STATUS_BAD_REQUEST = 400; // 잘못된 문법
    public static final int STATUS_UNAUTHORIZED = 401; // 미승인=비인증, 사용자 식별 불가능
    public static final int STATUS_FORBIDDEN = 403; // 권한 없음. 사용자 식별 가능
    public static final int STATUS_NOT_FOUND = 404; // 리소스 없음
    public static final int STATUS_METHOD_NOT_ALLOWED = 405; // 사용할 수 없는 메소드. GET과 HEAD에는 불가
    public static final int STATUS_INTERNAL_SERVER_ERROR = 500; // 내부 서버 에러
    public static final int STATUS_SERVICE_UNAVAILABLE = 503; // 서버 유지 보수 중, 서버 과부하 등
}
