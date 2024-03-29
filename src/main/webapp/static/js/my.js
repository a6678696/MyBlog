function submitComment() {
    var content = $("#commentContent").val();
    var blogId = $("#commentBlogId").val();
    if (content == null || content == '') {
        $("#blogDetailsModal").text("请输入评论内容!!");
        return false;
    }
    $.post("/comment/add", {content: content, blogId: blogId},
        function (result) {
            if (result.success) {
                $("#blogDetailsModal").text("评论成功,审核通过后方可在下方显示!!");
                $("#commentContent").val("");
            } else {
                $("#blogDetailsModal").text(result.errorInfo);
            }
        }
    );
}

function getResult() {
    var content = $("#resultContent").val();
    if (content == null || content == '') {
        alert("请输入网页源代码!!");
        return false;
    }
    if (content.indexOf("<title>新商盟</title>") == -1) {
        $("#resultContent").val("");
        alert("你的输入不正确,请输入完整的网页源代码!!");
        return false;
    }
    $.post("/getResult", {str: content},
        function (result) {
            if (result.success) {
                $("#resultContent").val(result.result);
            } else {
                alert("你的输入错误,请输入完整的源代码!!");
                $("#resultContent").val();
            }
        }
    );
}

//用户点赞博客
function like() {
    var blogId = $("#commentBlogId").val();
    $.post("/like/add", {blogId: blogId},
        function (result) {
            if (result.success) {
                $("#blogDetailsModal").text("点赞成功!!");
                $("#like1").removeAttr("href");
                $("#like1").attr("href", "#");
                $("#likeButton").removeAttr("data-toggle");
                $("#likeButton").removeAttr("data-target");
                $("#like1").removeAttr("aria-label");
                $("#like1").attr("aria-label", "你已经点赞过这篇博客");
                $("#like1").removeAttr("style");
                $("#like1").attr("style", "float: right;color: blue");
                $("#i1").removeAttr("class");
                $("#i1").attr("class", "fa fa-thumbs-up");
            } else {
                $("#blogDetailsModal").text("点赞失败!!");
            }
        }
    );
}

//返回顶部图标出现或者消失
$(function () {
    $(function () {
        $(window).scroll(function () {
            if ($(window).scrollTop() > 100) {
                $("#gotop").fadeIn(1000);//一秒渐入动画
            } else {
                $("#gotop").fadeOut(1000);//一秒渐隐动画
            }
        });

        $("#gotop").click(function () {
            $('body,html').animate({scrollTop: 0}, 1000);
        });
    });
});

$(document).ready(function () {
    //全部图片
    $("img").addClass("img-responsive");
    $("img").addClass("pimg");
    $("img").addClass("img-thumbnail");
    //首页图片
    $(".indexFirstImage").removeClass("pimg");
    //logo
    $("#img1").removeClass("img-responsive");
    $("#img1").removeClass("pimg");
    $("#img1").removeClass("img-thumbnail");
    //备案图片
    $("#img2").removeClass("img-responsive");
    $("#img2").removeClass("pimg");
    $("#img2").removeClass("img-thumbnail");
    //完字图片
    $("#img3").removeClass("img-responsive");
    $("#img3").removeClass("pimg");
    $("#img3").removeClass("img-thumbnail");
    //返回顶部图片
    $("#gotop").removeClass("img-responsive");
    $("#gotop").removeClass("pimg");
    //评论人图片
    $(".img-rounded").removeClass("pimg");
    //鼠标悬停更换返回顶部图片
    $(".imgdiv").mouseover(function () {
        $(".imgsrc").attr("src", "/static/images/top4.png");
    });
    $(".imgdiv").mouseout(function () {
        $(".imgsrc").attr("src", "/static/images/top2.png");
    });
    $("table").addClass("table table-striped table-hover table-bordered");
    $.ajax({
        url: "/readCodeFamily",
        type: "post",
        success: function (result) {
            var codeFamily = result.codeFamily;
            $("pre code").css("font-family", codeFamily);
        },
    });
    $("img").attr("alt", "请刷新本页面以查看图片，如果图片已失效请到对应博客的评论区下给我留言┭┮﹏┭┮");
    $('pre code').each(function (i, block) {
        hljs.highlightBlock(block);
    });
});

//图片放大
$(function () {

    $(".pimg").click(function () {
        var _this = $(this);//将当前的pimg元素作为_this传入函数
        imgShow("#outerdiv", "#innerdiv", "#bigimg", _this);
    });
});

function imgShow(outerdiv, innerdiv, bigimg, _this) {

    var src = _this.attr("src");//获取当前点击的pimg元素中的src属性
    $(bigimg).attr("src", src);//设置#bigimg元素的src属性
    /*获取当前点击图片的真实大小，并显示弹出层及大图*/
    $("<img/>").attr("src", src).load(function () {
        var windowW = $(window).width(); //获取当前窗口宽度
        var windowH = $(window).height(); //获取当前窗口高度
        var realWidth = this.width; //获取图片真实宽度
        var realHeight = this.height; //获取图片真实高度
        var imgWidth, imgHeight;
        var scale = 0.8; //缩放尺寸，当图片真实宽度和高度大于窗口宽度和高度时进行缩放
        if (realHeight > windowH * scale) { //判断图片高度
            imgHeight = windowH * scale; //如大于窗口高度，图片高度进行缩放
            imgWidth = imgHeight / realHeight * realWidth; //等比例缩放宽度
            if (imgWidth > windowW * scale) { //如宽度扔大于窗口宽度
                imgWidth = windowW * scale; //再对宽度进行缩放
            }
        } else if (realWidth > windowW * scale) { //如图片高度合适，判断图片宽度
            imgWidth = windowW * scale; //如大于窗口宽度，图片宽度进行缩放
            imgHeight = imgWidth / realWidth * realHeight; //等比例缩放高度
        } else { //如果图片真实高度和宽度都符合要求，高宽不变
            imgWidth = realWidth;
            imgHeight = realHeight;
        }
        $(bigimg).css("width", imgWidth); //以最终的宽度对图片缩放
        var w = (windowW - imgWidth) / 2; //计算图片与窗口左边距
        var h = (windowH - imgHeight) / 2; //计算图片与窗口上边距
        $(innerdiv).css({"top": h, "left": w}); //设置#innerdiv的top和left属性
        $(outerdiv).fadeIn("fast"); //淡入显示#outerdiv及.pimg
    });

    $(outerdiv).click(function () {  //再次点击淡出消失弹出层
        $(this).fadeOut("fast");
    });
}