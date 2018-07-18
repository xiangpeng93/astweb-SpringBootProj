<template>
    <main-layout>
        <div :style="{display:ShowList}">
            <div class="container">
                <button class="btn btn-success pull-right" @click="publishCommunity()">我要发帖</button>
            </div>
            <br>
            <div class="container">
                <ul class="list-group">
                    <li class="list-group-item" v-for="item in items"><a href="#" @click="linkToBody(item.id)">{{item.name}}</a>
                    </li>
                </ul>
            </div>
        </div>

        <div class="container" :style="{display:ShowAddCommunity}">
            <div class="form-group">
                <!-- Text input-->
                <label for="community_name">标题</label>
                <br/>
                <input id="community_name" name="community_name" type="text" placeholder="输入标题" class="form-control">
            </div>
            <div>
                <div id="editDivHead">
                </div>
                <div name="community_body" id="editDivBody" style="height:500px;margin:5px;width: 100%">
                </div>
            </div>
            <hr>
            <div class="text-right container">
                <button class="btn btn-primary " style="margin:5px;" @click="AddCommunityInfo()">发布信息</button>
            </div>

        </div>
    </main-layout>
</template>

<script>
    import MainLayout from '../layouts/Main.vue'
    import wangEdit from '../../wang/wangEditor.js';

    var editor2 = new wangEdit('#editDivHead', '#editDivBody');
    export default {
        components: {
            MainLayout
        },
        data() {
            return {
                items: [],
                host: window.location.host,
                webHost: window.location.host,
                ShowList: "block",
                ShowAddCommunity: "none"
            }
        },
        mounted(){
            this.$nextTick(function () {
                this.createEdit();
                try {
                    var submitUrl = "http://" + this.host + "/QueryCommunityInfo";
                    var htmlobj = $.ajax({
                        type: 'GET',
                        url: submitUrl,
                        data: {Begin: '0', Count: '5'},
                        async: false
                    });

                    var resultData = JSON.parse(htmlobj.responseText);
                    for (var i = 0; i < resultData.length; i++) {
                        this.items.push({
                            'id': resultData[i].id,
                            'name': resultData[i].communityHead,
                        });
                    }
                }
                catch (e) {
                    console.log(e)
                }
            })
        },
        methods: {
            isPC:function(){
                var sUserAgent = navigator.userAgent.toLowerCase();
                var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";
                var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
                var bIsMidp = sUserAgent.match(/midp/i) == "midp";
                var bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";
                var bIsUc = sUserAgent.match(/ucweb/i) == "ucweb";
                var bIsAndroid = sUserAgent.match(/android/i) == "android";
                var bIsCE = sUserAgent.match(/windows ce/i) == "windows ce";
                var bIsWM = sUserAgent.match(/windows mobile/i) == "windows mobile";
                if((bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM))
                {
                    return false;
                }
                return true;
            },
            createEdit: function () {
                editor2.customConfig.uploadImgServer = 'http://' + this.host + '/uploadPic'  // 上传图片到服务器
                editor2.customConfig.zIndex = 1
                editor2.customConfig.uploadImgTimeout = 60000
                // 自定义菜单配置
               if(!this.isPC()) {
                   editor2.customConfig.menus = [
                       'emoticon',  // 表情
                       'image',  // 插入图片
                   ]
               }
                editor2.create()
                editor2.customConfig.debug = location.href.indexOf('wangeditor_debug_mode=1') > 0
            },
            publishCommunity: function () {
                if (this.getCookie("userId") == "") {
                    alert("请先登录！");
                }
                else
                {
                    this.ShowList = "none"
                    this.ShowAddCommunity = "block"
                }
            },
            linkToBody: function (id) {
                window.location = "/show_community_page?id=" + id;
            },//获取cookie
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
            getFirstImgUrl: function (body) {
                var re = /src=\"([^\"]*?)\"/i;
                var arr = body.match(re);
                if (arr != undefined && arr.length > 0) {
                    console.log(arr);
                    return arr[1];
                }
                else {
                    return this.defaultImg;
                }
            },
            AddCommunityInfo: function () {
                try {
                    var submitUrl = "http://" + this.host + "/CommunityAdd";
                    console.log(submitUrl);
                    var htmlobj = $.ajax({
                        type: 'POST',
                        url: submitUrl,
                        dataType: 'json',
                        data: {
                            id: this.id,
                            communityHead: $("#community_name").val(),
                            communityAuthor: this.getCookie("userId"),
                            communityGroup: "",
                            communityBody: editor2.txt.html()
                        },
                        async: false
                    });
                    if (htmlobj.responseText != "-1" && htmlobj.responseText != "-2") {
                        this.id = htmlobj.responseText;
                        alert("发布成功");
                    }
                    else {
                        alert("发布失败")
                    }
                }
                catch (error) {
                    console.log(error)
                }
            }
        }

    }
</script>
