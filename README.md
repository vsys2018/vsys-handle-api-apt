# vsys-handle-api-apt
Sử dụng kết hợp với lệnh giao tiếp ngoại vi Media:
```
I101'R:com.vsys.android.vsyshandleapi,{getType},{position},{keyName},{api}'
```
Trong đó:
1. getType: là kiểu lấy dữ liệu.
    - 1 lấy trực tiếp api trả về kết quả
    - 2 lấy kết quả trước đó đã lấy thông qua getType 1.
2. position: vị trí phần tử cần lấy
    - -1 lấy vị trí phần tử cuối cùng
3. keyName: tên đối tượng cần lấy.
4. api: link api. Có thể có http:// hoặc không
