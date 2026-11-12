## 1. Vị trí sử dụng từng pattern

| Pattern | Lớp | Vị trí sử dụng |
|---|---|---|
| Factory | `DeviceFactory` | `create(type, name)` nhận chuỗi loại thiết bị và trả về `Device`. Client không gọi trực tiếp `new SmartLight/SmartFan/SmartDoor` |
| Singleton | `HomeConfiguration` | Chỉ có một cấu hình chung, truy cập qua `getInstance()`. `SmartHomeFacade.goToSleep()` đọc `nightLightLevel` từ đây |
| Facade | `SmartHomeFacade` | `goToWork()` và `goToSleep()` gom nhiều thao tác trên đèn, quạt, cửa thành một lời gọi duy nhất |

## 2. Lợi ích đạt được

- **Factory:** client chỉ phụ thuộc vào `Device`, không phụ thuộc lớp cụ thể. Thêm loại thiết bị mới chỉ cần thêm lớp và một nhánh `case`, không phải sửa client
- **Singleton:** cấu hình ngôi nhà chỉ tồn tại một bản duy nhất và được dùng nhất quán trong toàn ứng dụng
- **Facade:** client kích hoạt chế độ "work" hay "sleep" bằng một lời gọi mà không cần biết chi tiết từng thiết bị. Các thiết bị vẫn có thể được điều khiển riêng lẻ khi cần

## 3. Một hạn chế của thiết kế

`DeviceFactory.create()` trả về `Device`, nên khi tạo facade phải ép kiểu `(Lockable) door`. Cách này làm mất một phần lợi ích của abstraction và sẽ ném `ClassCastException` nếu truyền sai loại thiết bị

## 4. Phần G: Trả lời câu hỏi

**1. Factory đang che giấu quyết định thiết kế nào?**

Factory che giấu quyết định "chuỗi loại nào tương ứng lớp cụ thể nào và tạo nó ra sao" (lớp nào được dùng, cách gọi `new`, cách chuẩn hóa chuỗi). Client chỉ biết kiểu `Device`, nên có thể thêm hoặc đổi lớp thiết bị mà không phải sửa client

**2. Singleton bảo đảm điều gì, nhưng tạo ra rủi ro nào?**

- Bảo đảm: chỉ có một thể hiện duy nhất của `HomeConfiguration` và có một điểm truy cập chung là `getInstance()`. Với lớp holder, thể hiện được tạo lazy và an toàn luồng nhờ JVM
- Rủi ro: tạo trạng thái toàn cục ẩn (bất kì đâu cũng có thể đổi cấu hình), làm tăng coupling(khi sửa HomeConfiguration bằng 1 implementation khác thì phải sửa nhiều class gọi singleton này) và khó kiểm thử (khó mock)

**3. Facade khác Factory ở mục đích chính như thế nào?**

Factory là pattern khởi tạo (creational): nó tạo đối tượng đúng kiểu và che giấu lớp cụ thể. Facade là pattern cấu trúc (structural): nó không tạo thiết bị mà gom nhiều lời gọi trên các đối tượng có sẵn thành thao tác đơn giản, mức cao

**4. Nếu bỏ `SmartHomeFacade`, client sẽ phải biết thêm những chi tiết nào?**

- Có những thiết bị nào và thứ tự gọi chúng trong từng chế độ
- Cửa phải `lock()` chứ không phải `turnOff()`, nghĩa là phải biết đến `Lockable`
- Phải tự đọc `nightLightLevel` từ `HomeConfiguration` và in thông báo mức sáng
- Logic chế độ bị lặp ở nhiều nơi, mỗi lần đổi quy tắc phải sửa từng chỗ