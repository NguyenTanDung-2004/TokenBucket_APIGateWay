# 1. Clone từ github
```bash
git clone https://github.com/NguyenTanDung-2004/TokenBucket_APIGateWay.git
```

# 2. Chuyển sang nhánh main
``` bash
git checkout -b main
```

# 3. Cài đặt docker 
- Tải redis-image
```bash
docker pull redis
```
- Chạy redis
```bash
docker run -p 6379:6379 <id của redis image>
```

# 4. Chạy dự án
- Chạy lần lượt các file 
-  CartService\src\main\java\com\example\CartService\CartServiceApplication.java
-  IdentityService\src\main\java\com\example\IdentityService\IdentityServiceApplication.java
-  APIGateway\src\main\java\com\example\APIGateway\ApiGatewayApplication.java
