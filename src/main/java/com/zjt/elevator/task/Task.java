/*
package com.zjt.elevator.task;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjt.elevator.entity.ElevatorInfo;
import com.zjt.elevator.entity.LogInfor;
import com.zjt.elevator.mapper.ElevatorMapper;
import com.zjt.elevator.mapper.LogInforMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

*/
/**
 * @author zjt.
 * @version 1.0
 * @Date: 2021/4/7 12:46
 *//*

@Component
public class Task {

    @Autowired
    private ElevatorMapper elevatorMapper;

    @Autowired
    private LogInforMapper logInforMapper;


    @Scheduled(cron = "0/1 * * * * ?")
    public void doSomething() throws IOException {
        List<ElevatorInfo> elevatorInfos = elevatorMapper.selectList(new QueryWrapper<ElevatorInfo>().eq("elevator_mode",1));
        ElevatorInfo elevatorInfo = elevatorInfos.get(elevatorInfos.size() - 1);
        LocalDateTime status_updatetime = elevatorInfo.getStatus_updatetime();
        System.out.println("数据库时间" + status_updatetime + "当前时间" + LocalDateTime.now());

        if (status_updatetime == null || !CollectionUtils.isEmpty(elevatorInfos)) {
            //if (Duration.between(status_updatetime, LocalDateTime.now()).getSeconds() >= 30) {
                logInforMapper.insert(new LogInfor(null, 99, "唤起demo", LocalDateTime.now()));
               */
/* //String s = HttpUtil.get("http://elevator.viphk.ngrok.org/admin/app/dispatch_system/elevator_itlong_single_run.php");
                //String s = HttpUtil.get("http://127.0.0.1:80/admin/app/dispatch_system/elevator_itlong_single_run.php");
                HttpRequest request = HttpRequest.post("http://192.168.1.104:80/admin/app/dispatch_system/elevator_itlong_single_run.php");
                request.keepAlive(true);
                HttpResponse execute = request.execute();
                String body = execute.body();
                System.out.println("唤醒请求返回"+body);
                System.out.println("getRecord");*//*



                //start
                try {
                    // 1. 得到访问地址的URL
                    // 1. 得到访问地址的URL
                    URL url = new URL(
                            "http://39.108.153.214:80/admin/app/dispatch_system/elevator_itlong_single_run.php");
                    // 2. 得到网络访问对象java.net.HttpURLConnection
                    HttpURLConnection connection = (HttpURLConnection) url
                            .openConnection();
                    */
/* 3. 设置请求参数（过期时间，输入、输出流、访问方式），以流的形式进行连接 *//*

                    // 设置是否向HttpURLConnection输出
                    connection.setDoOutput(false);
                    // 设置是否从httpUrlConnection读入
                    connection.setDoInput(true);
                    // 设置请求方式
                    connection.setRequestMethod("GET");
                    // 设置是否使用缓存
                    connection.setUseCaches(true);
                    // 设置此 HttpURLConnection 实例是否应该自动执行 HTTP 重定向
                    connection.setInstanceFollowRedirects(true);
                    // 设置超时时间
                    connection.setConnectTimeout(3000);
                    // 连接
                    connection.connect();
                    // 4. 得到响应状态码的返回值 responseCode
                    int code = connection.getResponseCode();
                    // 5. 如果返回值正常，数据在网络中是以流的形式得到服务端返回的数据
                    String msg = "";
                    if (code == 200) { // 正常响应
                        // 从流中读取响应信息
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(connection.getInputStream()));
                        String line = null;
                        while ((line = reader.readLine()) != null) { // 循环从流中读取
                            msg += line + "\n";
                        }
                        reader.close(); // 关闭流
                    }
                    // 6. 断开连接，释放资源
                    connection.disconnect();
                    // 显示响应结果
                    System.out.println(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //end
            //}
        } else {
            logInforMapper.insert(new LogInfor(null, 99, "无更新时间", LocalDateTime.now()));
        }
        //System.out.println("any");
        //String s = HttpUtil.get("http://elevator.viphk.ngrok.org/admin/app/dispatch_system/elevator_itlong_single_run.php");
        */
/*List<ElevatorInfo> elevatorInfos = elevatorMapper.selectList(new QueryWrapper<>());
        //String s = HttpUtil.get("http://elevator.viphk.ngrok.org/admin/app/dispatch_system/elevator_itlong_single_run.php");
        System.out.println(elevatorInfos);
    }*//*

    }
}



*/
