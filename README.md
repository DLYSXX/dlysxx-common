# dlysxx-common
Common utils project

## 
This project summarizes common used util tool classes.

```
<dependency>
  <groupId>cn.dlysxx.www.common</groupId>
  <artifactId>dlysxx-common</artifactId>
  <version>1.0.1</version>
</dependency>
```

## Project structure
<img width="706" alt="image" src="https://user-images.githubusercontent.com/61540695/163087019-39adfe5f-7d9a-4397-96ea-16ff7184b091.png">

* AESCryptoUtil.java
  - encryptString
  - decryptString
  - encryptFile
  - decryptFile
* DateUtil.java
  - nowZonedDateTime
  - nowLocalDateTime
  - nowLocalDate
  - nowLocalTime
  - nowDate
  - nowCalendar
  - nowYearMonth
  - nowString
  - toZonedDateTime
  - toLocalDateTime
  - toLocalDate
  - toLocalTime
  - toDate
  - toCalendar
  - toYearMonth
  - toString
  - isIntime
* FileUtil.java
  - inputStreamToFile
* ZipUtil.java
  - compressToZip
  - decompressFromZip
* StringUtil.java
  - conversion
  - mask
