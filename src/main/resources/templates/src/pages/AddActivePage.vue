<template>
    <main-layout>
        <div class="text-center">
            <h2>信息发布平台</h2>
        </div>
        <hr>
        <div class="container">

            <div class="form-group">
                <!-- Text input-->
                <label for="active_name">活动标题</label>
                <br/>
                <input id="active_name" name="active_name" type="text" placeholder="输入标题" class="form-control">
            </div>
            <div class="form-group">
                <!-- Text input-->
                <label>活动场次数量</label>
                <br/>
                <input id="active_number" name="active_number" type="text" placeholder="输入数量" class="form-control">
            </div>
            <div class="form-group">
                <!-- Text input-->
                <label>每场人数限制</label>
                <br/>
                <input id="active_person_number" name="active_person_number" type="text" placeholder="输入人数"
                       class="form-control">
            </div>

        </div>
        <hr>
        <div class="container">
            <div id="editDivHead">
            </div>
            <div name="active_body" id="editDivBody" style="height:500px;margin:5px;">
            </div>
        </div>
        <hr>
        <div class="text-right container">
            <button class="btn btn-primary " style="margin:5px;" @click="AddActiveInfo()">保存并发布信息</button>
        </div>
    </main-layout>
</template>

<style scoped>
    @import '../../wang/wangEditor.css';
</style>

<script>
    import wangEdit from '../../wang/wangEditor.js';
    import MainLayout from '../layouts/Main.vue';

    var editor2 = new wangEdit('#editDivHead', '#editDivBody');

    export default {
        components: {
            MainLayout
        },
        data()
        {
            return {
                host: "127.0.0.1:8080",
                id: 0
            }
        },
        mounted(){
            this.$nextTick(function () {
                this.createEdit();
                this.id = this.GetQueryString("id")
                var submitUrl = "http://" + this.host + "/QueryActiveInfoById";
                var htmlobj = $.ajax({type: 'GET', url: submitUrl, data: {id: this.id}, async: false});
                console.log(htmlobj.responseText);
                var resultData = JSON.parse(htmlobj.responseText);
                editor2.txt.html(resultData.activeBody);
                $("#active_name").val(resultData.activeHead);
                $("#active_number").val(resultData.activeUserCount);
                $("#active_person_number").val(resultData.activeUserCount);
            })
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
            },
            ActiveRegister: function () {
                $('#active_modal').modal('show');
            },
            createEdit: function () {
                editor2.customConfig.uploadImgServer = 'http://' + this.host + '/uploadPic'  // 上传图片到服务器
                editor2.customConfig.zIndex = 1
                editor2.customConfig.uploadImgTimeout = 60000
                editor2.create()
                editor2.customConfig.debug = location.href.indexOf('wangeditor_debug_mode=1') > 0
            },

            getEditText: function () {
                console.log("get edit");
                console.log(editor2.txt.html());
            },
            //获取cookie
            getCookie: function (cname) {
                try {
                    var name = cname + "=";

                    var ca = document.cookie.split(';');
                    for (var i = 0; i < ca.length; i++) {
                        var c = ca[i];
                        while (c.charAt(0) == ' ') c = c.substring(1);
                        if (c.indexOf(name) != -1) return c.substring(name.length, c.length);
                    }
                }
                catch (error) {
                    console.log(error)
                }
                return "";
            },
            AddActiveInfo: function () {
                var submitUrl = "http://" + this.host + "/ActiveAdd";
                console.log(submitUrl);
                var htmlobj = $.ajax({
                    type: 'GET',
                    url: submitUrl,
                    dataType: 'json',
                    data: {
                        id: this.id,
                        activeHead: $("#active_name").val(),
                        activeAuthor: this.getCookie("userId"),
                        activeUserCount: $("#active_number").val(),
                        activeBody: editor2.txt.html(),
                        activeCount: $("#active_number").val()
                    },
                    async: false
                });
                alert(htmlobj.responseText);
            }
        }
    }
</script>
