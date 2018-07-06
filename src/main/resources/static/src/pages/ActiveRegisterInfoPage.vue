<template>
    <main-layout>
        <div class="container" style="padding-bottom:10px;">
            <h2 class="text-center">{{ head }}</h2>
            <br>
            <hr>
            <div style="white-space: pre-wrap;word-wrap: break-word;">
                <table class="table">
                    <thead>
                    <tr>
                        <th>姓名</th>
                        <th>年龄</th>
                        <th>电话</th>
                        <th>活动名称</th>
                        <th>场次</th>
                        <th>报名时间</th>
                        <th><button class="btn btn-info">导出数据</button></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="row in registerInfoList">
                        <td>{{row.userName}}</td>
                        <td>{{row.userAge}}</td>
                        <td>{{row.userPhone}}</td>
                        <td>{{row.activeName}}</td>
                        <td>{{row.activeSession}}</td>
                        <td>{{row.joinDate}}</td>
                        <td><button class="btn btn-warning">删除</button></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </main-layout>
</template>

<script>
    import MainLayout from '../layouts/Main.vue'
    export default {
        components: {},
        data: function () {
            return {
                registerInfoList: [],
                host: window.location.host+":8080",
                head:"活动名称报名信息展示",
            }
        },
        methods: {
            GetQueryString: function (name) {
                var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
                var r = window.location.search.substr(1).match(reg);  //获取url中"?"符后的字符串并正则匹配
                var context = "";
                if (r != null)
                    context = r[2];
                reg = null;
                r = null;
                return context == null || context == "" || context == "undefined" ? "" : context;
            }
        },
        mounted()
        {
            var submitUrl = "http://" + this.host + "/QueryJoinInfo";
            var htmlobj = $.ajax({
                type: 'GET',
                url: submitUrl,
                data: {activeId: decodeURI(this.GetQueryString("id"))},
                async: false
            });
            console.log(htmlobj.responseText);
            this.registerInfoList = JSON.parse(htmlobj.responseText);
        }
    }
</script>
