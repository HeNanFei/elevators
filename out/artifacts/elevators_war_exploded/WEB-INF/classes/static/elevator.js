const ELE_NUM = 5;    // 电梯数：0~4
const MAX_TIME = 300;//时间最大阈值
const MAX_FLOOR = 20;  
const MIN_FLOOR = 1;
const STOP_TIME = 4;

/*基本变量*/
var queue = new Array(ELE_NUM);       //存放五个电梯的任务
var direction = new Array(ELE_NUM);  //电梯方向 上true下false
var running = new Array(ELE_NUM);       //判断电梯是否运行 
var stop = new Array(ELE_NUM);         //当前停留状态
var currentFloor = new Array(ELE_NUM);    //目前层数 初始为1
var isUpdated = new Array(ELE_NUM);     //电梯状态是否更新
var timer = new Array(ELE_NUM);      //五部电梯线程计时器 每隔一秒执行一次主函数
var NeedToStop = new Array(ELE_NUM);     // 停靠标志

/*挂起相关变量*/
var sameDirection = new Array(ELE_NUM);     //请求是否同向
var waitFloor = new Array(ELE_NUM);     //存放挂起等待的dial楼层
var waitDirection = new Array(ELE_NUM);    //存放挂起等待的dial方向
var waitAging = new Array(ELE_NUM);    //存放挂起等待的dial老化级别
var isWait = new Array(ELE_NUM);    //是否存在挂起等待的dial


/*便于按键亮灭控制*/
var inside = new Array(ELE_NUM); 
var outsideUp = new Array(ELE_NUM);
var outsideDown = new Array(ELE_NUM);
var INSIDE = 0;
var OUTSIDE_UP = 1;
var OUTSIDE_DOWN = 2;

var current_floor  = 0;
var current_status = 0;

$(function () {
    setInterval('doIt()',5000);
})

function doIt() {
    $.ajax
    ({
        url: "http://localhost:8999/getData",
        dataType: "json",
        type: "get",
        success:function(res){
            //alert(JSON.stringify(res.current_layer));
            debugger
            current_status = res.door_status;
            if(res.current_layer !== current_floor){
                initLayer(res.current_layer);
                current_floor = res.current_layer;
            }else{
                //initLayer(1);
                if(current_status === 0){
                    close(3);
                }else{
                    open(3);
                }
            }
            console.log(res);
        },
        error:function(){
            alert('failed!');
        },
    });
}

/*初始化*/
function init() {

    console.log('adjust');
    for (var i = 0; i < ELE_NUM; i++) {
        
        queue[i] = new Array();
        direction[i] = true;
        running[i] = false;
        currentFloor[i] = 1;
        stop[i] = true;
        isUpdated[i] = false;
        inside[i] = new Array(MAX_FLOOR);
        outsideDown[i] = new Array(MAX_FLOOR);
        outsideUp[i] = new Array(MAX_FLOOR);
        timer[i] = setInterval("main(" + i + ")", 1000);
    }
}
$(document).ready(init);



/*按键响应*/
//响应紧急电话键
$(".call").click(function () {
    alert("正在呼救！");
});

//响应紧急情况键
$(".emergency").click(function () {
    alert("紧急情况！");
});

//响应开门键
$(".open").click(function () {
    var this_id = $(this)[0].id;
    openDoorbyButton(Number(this_id.substr(5)));
});

//响应关门键
$(".close").click(function () {
    var this_id = $(this)[0].id;
    close(Number(this_id.substr(6)));
});

//响应上行键
$(".goup").click(function () {
    var this_id = $(this).parent()[0].id; //只有通过id访问是一个元素，通过标签和class访问的是一个数组（元素列表）
    var pressedFloor = Number(this_id.substr(5)); //从下标为5的位置开始取
    var bestElevator = best(pressedFloor, true);

    if (outsideUp[bestElevator][pressedFloor] != 1) {
        outsideUp[bestElevator][pressedFloor] = 1;
        dial(pressedFloor, true);
        $("#floor" + pressedFloor + " td:nth-child(1)").addClass("on");
    }
});

//响应下行键
$(".godown").click(function () {
    //alert('jakdlf');
    var this_id = $(this).parent()[0].id;
    var pressedFloor = Number(this_id.substr(5));
    var bestElevator = best(pressedFloor, false);

    if (outsideDown[bestElevator][pressedFloor] != 1) {
        outsideDown[bestElevator][pressedFloor] = 1;
        dial(pressedFloor, false);
        $("#floor" + pressedFloor + " td:nth-child(2) ").addClass("on");
    } 
});

// 响应数字键
$(".dial .button").click(function () {
    var this_class = $(this)[0].className;
    var parent_id = $(this).parent()[0].id;
    var pressedFloor = Number(this_class.substr(11));
    var n = Number(parent_id.substr(7));
    ;
    console.log("按下的楼层数为：" + pressedFloor);

    if (!running[n]) {
        var up = (pressedFloor > currentFloor[n]) ? true : false;
        startRun(n, pressedFloor, up);
    }
    if (inside[n][pressedFloor] != 1) {
        inside[n][pressedFloor] = 1;
        AddInQueue(n, pressedFloor);
        $(this).addClass("pressed");
    }
});

function initLayer(elevatorNumber) {
    var this_class = $(this)[0].className;
    //var parent_id = $(this).parent()[0].id;
    debugger;
    var pressedFloor = elevatorNumber;
   /* var n = Number(parent_id.substr(7));
    ;*/

   var n = 3;
   var parent_id = 2;
    console.log("按下的楼层数为：" + pressedFloor);

    if (!running[n]) {
        var up = (pressedFloor > currentFloor[n]) ? true : false;
        startRun(n, pressedFloor, up);
    }
    if (inside[n][pressedFloor] != 1) {
        inside[n][pressedFloor] = 1;
        AddInQueue(n, pressedFloor);
        $(this).addClass("pressed");
    }
}




/*开关门*/
//开门动画
function open(n) {
    debugger;
    $("#E" + n + " .leftdoor").css("left", "0%");
    $("#E" + n + " .rightdoor").css("left", "55%");
}

//关门动画
function close(n) {
    debugger;
    $("#E" + n + " .leftdoor").css("left", "25%");
    $("#E" + n + " .rightdoor").css("left", "40%");
}

//自动开门
function openDoor(n) {
    if (timer[n]) {
        clearInterval(timer[n]);
    }
    setTimeout(function () {
        open(n);
        //4s后关门 3s后设置timer timer为1s（所以关门开门时间都是4s）
        setTimeout(function () {
            close(n);
            setTimeout(function () {
                timer[n] = setInterval("main(" + n + ")", 1000);
            }, 2000);
        }, 2000);
    }, 1000);
}

//手动开门
function openDoorbyButton(n) {

    if (NeedToStop[n]) {//扫描数组确定是否需要停 
        if (timer[n]) {  //需要 允许手动开门
            clearInterval(timer[n]);
        }
        open(n);

        // 每过1秒检测一下上一次自动关门时候设置的Timer
        // 3s后关门 2s后设置timer timer为1s（所以关门开门时间都是3s）

        setTimeout(function () {
            if (timer[n]) {
                clearInterval(timer[n]);
            }
            setTimeout(function () {
                if (timer[n]) {
                    clearInterval(timer[n]);
                }
                setTimeout(function () {
                    close(n);
                    setTimeout(function () {
                        timer[n] = setInterval("main(" + n + ")", 1000);
                    }, 2000);
                }, 1000);
            }, 1000);
        }, 1000);
    }
    else {
        alert("危险！电梯运动过程中禁止开门！");
    }
}

//到达后熄灭对应按钮
function lightsOff(n, floor, way) {

    if (way == OUTSIDE_UP && $("#floor" + floor + " td")[1])
        $("#floor" + floor + " td:nth-child(1) ").removeClass("on"); 

    // $("#floor" + floor + " td a").removeClass("on"); //上下同时都灭了 不可行

    else if (way == OUTSIDE_DOWN && $("#floor" + floor + " td")[2])

        $("#floor" + floor + " td:nth-child(2) ").removeClass("on"); 



    else if (way == INSIDE && $("#dial" + floor))

        $("#dialpad" + n + " .dial" + floor).removeClass("pressed");

}




/*调度*/
// 呼梯
function dial(floor,up) {
    var bestElevator = best(floor,up);

    if (bestElevator == -1) {
        hangupDial(floor,up);
    }
    else {
        answerDial(bestElevator, floor,up);
    }
}

//响应dial
function answerDial(bestElevator, floor,up) {
    if (!running[bestElevator]) {
        startRun(bestElevator, floor,up);
    }  
    AddInQueue(bestElevator, floor);
}

//挂起dial
function hangupDial(floor,up) {
    waitFloor.push(floor);
    waitDirection.push(up);
    waitAging.push(0);
    isWait = true;
    //更新优先级
    for (var i = 0; i < waitDirection.length; i++) {
        waitAging[i]++;
    }
}

//唤醒等待的dial，优先响应老化级别>1的任务
function arouseWaiting(n) {
    var arouseIndex = 0;
    var temp_CostTime = 0;
    var costTime = MAX_TIME;
    for (var i = 0; i < waitDirection.length; i++) {
        temp_CostTime = timeCost(i, waitFloor[i], waitDirection[i]);

        //考虑老化
        if (waitAging[i] > 1 && temp_CostTime != -1) {
            answerDial(n, waitFloor[i], waitDirection[i]);
            waitFloor.splice(i, 1);
            waitDirection.spice(i, 1);
            waitAging.splice(i, 1);

            if (waitDirection.length == 0) {
                Bwait = false;
            }
            return;
        }
        //正常情况 按最短时间优先
        if (costTime > temp_CostTime && temp_CostTime != -1) {
            arouseIndex = i;
            costTime = temp_CostTime;
        }
    }
    if (costTime != MAX_TIME) {
        answer(n, waitFloor[arouseIndex], waitDirection[arouseIndex]);
        waitFloor.splice(arouseIndex, 1);
        waitDirection.spice(arouseIndex, 1);
        waitAging.splice(arouseIndex, 1);

        if (waitDirection.length == 0) {
            Bwait = false;
        }
    }
}  

//改变等待状态
function startRun(n,floor,up) {
    if (!running[n]) {
        if (currentFloor[n] > floor) {
            direction[n] = false;
            sameDirection[n] = (up == true) ? false : true;
        }
        else {
            if (currentFloor[n] < floor) {
                direction[n] = true;
                sameDirection[n] = (up == false) ? false : true;
            }
            else {
                direction[n] = (up ==true) ? true : false;
                sameDirection[n] = true;
            }
        }
        if (direction[n]) {
            $("#uplight" + n).addClass("turnon"); //上行电梯内信号灯
        }
        else {
            $("#downlight" + n).addClass("turnon");    //下行电梯内信号灯
        }
        running[n] = true;
    }
}

//选择最优电梯
function best(floor,up) {
    var bestElevator = 0;
    var temp = 0;
    var cost = MAX_TIME;

    for (var i = 0; i < ELE_NUM; i++) {
        temp = timeCost(i, floor,up);
        if (cost > temp && temp != -1) {
            cost = temp;
            bestElevator = i;
        }
    }
    if (cost == MAX_TIME) {//每个电梯均不满足 则挂起请求
        bestElevator = -1;
    }
    ;
    return bestElevator;
}

//获取任务队列中最高楼层
function getMax(n) {
    if (queue[n].length <= 0) {
        throw new Error("can't get max from an empty array.");
        return false;
    }
    if (queue[n].length == 1) {
        return queue[n][0];
    } else {
        var max = queue[n][0];
        for (var i in queue[n]) {
            if (queue[n][i] > max) {
                max = queue[n][i];
            }
        }
        return max;
    }
}

//获取任务队列中最低楼层
function getMin(n) {
    if (queue[n].length <= 0) {
        throw new Error("can't get min from an empty array.");
        return false;
    }
    if (queue[n].length == 1) {
        return queue[n][0];
    } else {
        var min = queue[n][0];
        for (var i in queue[n]) {
            if (queue[n][i] < min) {
                min = queue[n][i];
            }
        }
        return min;
    }
}

//从队列中删除已到楼层
function removeFloor(n, floor) {
    debugger
    console.log("要删除的数字是" + floor);
    if (queue[n].indexOf(floor) < 0) {
        throw new Error("对象不存在");
        return false;
    }
    if (queue[n].length <= 0) {
        throw new Error("任务队列已空");
        return false;
    }
    for (var i = 0, len = queue[n].length; i < len; i++) {
        if (queue[n][i] == floor) {
            for (var j = i; j < len - 1; j++) {
                queue[n][j] = queue[n][j + 1];
            }
            queue[n].pop();
            break;
        }
    }
}

//计算两楼层间需停下楼层数
function stopSum(a, b, n) {
    if (a > b) {
        var temp = a;
        a = b;
        b = temp;
    }
    var stopNum = [];
    for (var i in queue[n]) {
        if (queue[n][i] <= b && queue[n][i] >= a && stopNum.indexOf(queue[n][i] < 0)) {

            stopNum.push(queue[n][i]);
        }
    }
    return stopNum.length;
}

//计算时间花费
function timeCost(n, pressedFloor) {
    var cost = 0;

    if (!running[n]) {   //如果电梯不在运动
        cost = Math.abs(pressedFloor - currentFloor[n]);//返回绝对值
    }
    if (direction[n]) {   //在运动 上行
        if  (currentFloor[n] < pressedFloor) {  //按下楼层再电梯上方 顺路  
            cost = pressedFloor - currentFloor[n] + 5 * stopSum(pressedFloor, currentFloor[n], n);
        }
        else {
            cost = -1;
        }
    }
    else {    //下行
        if (currentFloor[n] > pressedFloor) {
            distance = currentFloor[n] - pressedFloor + 5 * stopSum(pressedFloor, currentFloor[n], n);
        }
        else {
            cost = -1;
        }
    }
    return cost;
}

//加入电梯内调度任务队列
function AddInQueue(n, floor) {
    queue[n].push(floor);
    queue[n].sort(function (a, b){
        return a - b;
    });
}





/*状态更新*/
//向上运动时更新当前层
function moveUp(n) {
    if (currentFloor[n] < MAX_FLOOR)
        currentFloor[n]++;
}

//向上运动时更新当前层
function moveDown(n) {
    if (currentFloor[n] > MIN_FLOOR)
        currentFloor[n]--;
}

//更新状态
function updateStatus(n) {
    var length = queue[n].length;
    //队列为空，则电梯：等待状态，非运行态
    if (length == 0) {
        if (direction[n]) {
            $("#uplight" + n).removeClass("turnon");
        } else {
            $("#downlight" + n).removeClass("turnon");
        }
        running[n]=0;
        return true;
    }
    //向上走到顶了，但仍旧队列存在请求,则电梯反向
    if (direction[n] == true && queue[n][length - 1] < currentFloor[n]) {
        direction[n] = false;
        //电梯内部方向灯改变方向
        $("#uplight" + n).removeClass("turnon");
        $("#downlight" + n).addClass("turnon");
        return true;
    }
    //向下走到底了，但队列仍旧存在请求，则电梯反向
    if (direction[n] == false && queue[n][0] > currentFloor[n]) {
        direction[n] = true;
       //电梯内部方向灯改变方向
        $("#downlight" + n).removeClass("turnon");
        $("#uplight" + n).removeClass("turnon");
        return true;
    }
    return false;
}

//更新门、显示屏
function updateFloorInfo(n) {
    //门的上下移动效果
    var ElevatorMove = (currentFloor[n] - 1) * 588 * 0.05;
    $("#E" + n + " .door").css("bottom", ElevatorMove + "px");

    //更新内部显示屏和门打开后的currentFloor
    if (currentFloor[n] > 0) {
        $("#floorOnScreen" + n).text("" + currentFloor[n]);
    }
}

//主函数 控制整体运行
function main(n, floor) {
    
    if (running[n]) { //已经升到currentFloor的状态
        debugger
        NeedToStop[n] = false;
        if (queue[n].indexOf(currentFloor[n]) > -1) {    // if elevator is right where it's called
            if (inside[n][currentFloor[n]] == 1) {//如果是内部到达
                lightsOff(n, currentFloor[n], INSIDE); //熄灭按键
                removeFloor(n, currentFloor[n]);//移除
                inside[n][currentFloor[n]] = 0;//更新置0
                NeedToStop[n] = true;//刷新stop 准备停下            
            }
            if (direction[n]) {//电梯是向上状态
                if (outsideUp[n][currentFloor[n]] == 1) {//若是外部到达
                    lightsOff(n, currentFloor[n], OUTSIDE_UP);//灭掉外部
                    removeFloor(n, currentFloor[n]);
                    outsideUp[n][currentFloor[n]] = 0;
                    NeedToStop[n] = true;
                }
                if (outsideDown[n][currentFloor[n]] == 1 && currentFloor[n] == getMax(n)) {//到任务最顶
                    lightsOff(n, currentFloor[n], OUTSIDE_DOWN);
                    removeFloor(n, currentFloor[n]);
                    outsideDown[n][currentFloor[n]] = 0;
                    NeedToStop[n] = true;
                }
            }
            else {//电梯向下
                if (outsideDown[n][currentFloor[n]] == 1) {
                    lightsOff(n, currentFloor[n], OUTSIDE_DOWN);
                    removeFloor(n, currentFloor[n]);
                    outsideDown[n][currentFloor[n]] = 0;
                    NeedToStop[n] = true;
                }
                if (outsideUp[n][currentFloor[n]] == 1 && currentFloor[n] == getMin(n)) {//到达最底
                    lightsOff(n, currentFloor[n], OUTSIDE_UP);
                    removeFloor(n, currentFloor[n]);
                    outsideUp[n][currentFloor[n]] = 0;
                    NeedToStop[n] = true;    
                }
            }
            if (NeedToStop[n]) {//判断停不停  开门
                //到达后：更新队列、开关门、熄灯等一系列动作
                openDoor(n);
                //更新电梯运行状态
               // update[n] = updateStatus(n);
                //如果有楼层的状态更新，则意味着等待队列可以被唤起
                
            }
            else {//不停 向上还是向下？
                direction[n] ? moveUp(n) : moveDown(n);
            }
        }
        else {//电梯在该层不停 向上还是向下
            direction[n] ? moveUp(n) : moveDown(n);
        }
        isUpdated[n] = updateStatus(n); //更新电梯状态
        if (isUpdated[n] == true && isWait == true) {
            arouseWaiting(n);
            isUpdated[n] = false;
        }
    }
    updateFloorInfo(n);
}




