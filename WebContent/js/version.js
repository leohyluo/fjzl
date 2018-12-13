function newer(source, target) {
	var av1 = source.split(".");
    var av2 = target.split(".");

    if (source == target) {// 版本号相同，直接返回 false
        return false;
    }

    // 取长度小的作为对比次数
    var len = av1.length <= av2.length ? av1.length : av2.length;

    var result = 0;
    for (var i = 0; i < len; i++) {
        result = compare(parseInt(av1[i]), parseInt(av2[i]));
        if (result == 0) {
            // 如果相等则判断下一位
        } else if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    return false;
}

function compare(num1, num2){
	if (num1 > num2)
        return 1;
    if (num1 < num2)
        return -1;
    return 0;
}