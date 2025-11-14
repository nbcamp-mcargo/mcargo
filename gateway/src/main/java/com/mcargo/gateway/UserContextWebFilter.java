package com.mcargo.gateway;

//@Slf4j
//@Component

/// /@Order(2)
//@RequiredArgsConstructor
//public class UserContextWebFilter implements WebFilter {
//    @Value("${service.jwt.secret-key}")
//    private String secretKeyProp;
//
//    private SecretKey secretKey;
//
//    // 의존성 주입 후 초기화 수행하는 메서드
//    @PostConstruct
//    public void init() {
//        // AuthService와 동일한 방식으로 키 생성
//        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKeyProp));
//    }
//
//    // WebFlux용 UserContextFilter로 변환
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            return chain.filter(exchange);
//        }
//        String token = authHeader.substring(7);
//        try {
//            Claims claims = Jwts.parser()
//                .verifyWith(secretKey)
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
//
//            Long userId = Long.valueOf(claims.getSubject());
//            List<UserRole> roles = Arrays.stream(
//                    claims.get("role", String.class).split(","))
//                .map(String::trim)
//                .map(UserRole::valueOf)
//                .toList();
//            UserContextHolder.set(new UserContext(userId, roles));
//            UserContext userContext = UserContextHolder.get();
//            log.info("userContext.getUserId() = {}", userContext.getUserId());
//            log.info("userContext.getRoles() = {}", userContext.getRoles());
//        } catch (Exception e) {
//            log.warn("[UserContext] Header parsing error: {}", e.getMessage());
//        }
//
//        // reactive 환경에서는 doFinally로 Context 클리어
//        return chain.filter(exchange)
//            .doFinally(signalType -> UserContextHolder.clear());
//    }
//}
