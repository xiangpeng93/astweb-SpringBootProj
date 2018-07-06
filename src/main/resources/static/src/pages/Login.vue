<template>
    <main-layout>
        <div class="container">
            <h3 class="text-center">欢迎登录</h3>
            <hr></hr>
            <form>
                <fieldset>
                    <div class="form-group">
                        <!-- Text input-->
                        <label for="UserName">用户名称</label>
                        <input id="UserName" type="text" v-model="userName" placeholder="输入姓名" class="form-control">
                    </div>
                    <div class="form-group">
                        <label for="Password">密码</label>
                        <input type="password" class="form-control" v-model="passwd" id="Password" placeholder="输入密码">
                    </div>
                </fieldset>
            </form>
            <button @click="Login()" class="btn btn-default btn-block">&nbsp;登录&nbsp;</button>
            <hr/>
        </div>
    </main-layout>
</template>

<script>
    import MainLayout from '../layouts/Main.vue'
    export default {
        components: {
            MainLayout
        },
        data()
        {
            return {
                userName: "",
                passwd: "",
                host: window.location.host+":8080"
            }
        },
        methods: {
            //获取cookie
            getCookie: function (cname) {
                var name = cname + "=";
                var ca = document.cookie.split(';');
                for (var i = 0; i < ca.length; i++) {
                    var c = ca[i];
                    while (c.charAt(0) == ' ') c = c.substring(1);
                    if (c.indexOf(name) != -1) return c.substring(name.length, c.length);
                }
                return "";
            },
            //设置cookie
            setCookie: function (cname, cvalue, exdays) {
                var d = new Date();
                d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
                var expires = "expires=" + d.toUTCString();
                document.cookie = cname + "=" + cvalue + "; " + expires;
            },
            Login: function () {
                var submitUrl = "http://" + this.host + "/UserLogin";
                try {
                    var htmlobj = $.ajax({
                        type: 'GET',
                        url: submitUrl,
                        data: {userName: this.userName, passWord: this.passwd},
                        async: false
                    });
                    if (htmlobj.responseText == "0") {
                        alert("登录成功");
                        this.setCookie("userId", $("#UserName").val(), 365)
                        window.location.href = "/";//需要跳转的地址
                    }
                    else {
                        alert("登录失败，用户名密码错误！")
                    }

                }
                catch (error) {
                    console.log(error);
                }

            }
        },
        mounted(){
            if ($("html").height() > $("#mainDiv").height()) {
                $("#mainDiv").css("height", "100%");
            }
        }
    }
</script>
