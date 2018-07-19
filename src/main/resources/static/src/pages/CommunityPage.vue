<!--suppress ALL -->
<template>
    <main-layout>
        <div class="container" style="padding-bottom:10px;">
            <div style="min-height: 70vh">
                <h2 class="text-left" style="word-break: break-all; word-wrap: break-word;">{{ head }}</h2>
                <br>
                <span class="pull-left" style=" font-family: Helvetica;">
                创建时间 ： {{ time }}
            </span>
                <br>
                <span class="pull-left">发布者 ： {{ author }}</span>
                <hr>
                <div class="media">
                <span class="media-head">
                    <h4>{{author}}：</h4>
                </span>
                    <div class="media-body" style="white-space: pre-wrap;word-wrap: break-word;">
                        <span v-html="body"></span>
                    </div>
                </div>
                <div class="media" v-for="item in items">
                <span class="media-head">
                    <h4>{{item.author}}：</h4>
                </span>
                    <div class="media-body" style="white-space: pre-wrap;word-wrap: break-word;">
                        <span v-html="item.body"></span>
                    </div>
                </div>
            </div>
            <div class="row" >
                <div class="container col-lg-10" style=" margin-top: 5px;">
                    <div>
                        <div id="editDivHead">
                        </div>
                        <div name="community_body" id="editDivBody" style="height:100px; margin:3px;width: 100% ;background-color: #ded7d7">
                        </div>
                    </div>
                </div>
                <div class="col-lg-2" style=" margin-top: 5px;">
                    <button class="btn btn-info pull-right" @click="publishComment()">发布评论</button>
                </div>
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
        data: function () {
            return {
                id: 0,
                head: "",
                body: "Test",
                author: "test",
                host: window.location.host,
                items: [],
                commentMsg: ""
            }
        },
        methods: {
            publishComment: function () {
                if (this.getCookie("userId") != "") {
                    this.commentMsg = editor2.txt.html()
                    if (editor2.txt.text() != "") {
                        try {
                            var submitUrl = "http://" + this.host + "/CommentAdd";
                            var htmlobj = $.ajax({
                                type: 'POST',
                                url: submitUrl,
                                dataType: 'json',
                                data: {
                                    communityId: this.id,
                                    authorName: this.getCookie("userId"),
                                    commentBody:this.commentMsg
                                },
                                async: false
                            });
                        }
                        catch (error) {
                            console.log(error)
                        }
                        this.items.push(
                            {
                                author: this.getCookie("userId"),
                                body: this.commentMsg
                            }
                        )
                    }
                    this.commentMsg = '';
                    editor2.txt.html("");
                }
                else {
                    alert("请先登录！");
                }
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
            createEdit: function () {
                editor2.customConfig.uploadImgServer = 'http://' + this.host + '/uploadPic'  // 上传图片到服务器
                editor2.customConfig.zIndex = 1
                editor2.customConfig.uploadImgTimeout = 60000
                // 自定义菜单配置
                editor2.customConfig.menus = [
                    'image',  // 插入图片
                ]
                editor2.create()
                editor2.customConfig.debug = location.href.indexOf('wangeditor_debug_mode=1') > 0

            },
        },
        mounted()
        {
            this.createEdit()
            var submitUrl = "http://" + this.host + "/QueryCommunityInfoById";
            try {
                var htmlobj = $.ajax({
                    type: 'GET',
                    url: submitUrl,
                    data: {id: this.GetQueryString("id")},
                    async: false
                });
                var resultData = JSON.parse(htmlobj.responseText);
                this.id = resultData.id;
                this.body = resultData.communityBody;
                this.head = resultData.communityHead;
                this.time = resultData.communityPublishDate;
                this.author = resultData.communityAuthor;

                var submitUrl = "http://" + this.host + "/QueryCommentInfoByCommunityId";
                var htmlobj = $.ajax({
                    type: 'GET',
                    url: submitUrl,
                    data: {communityId: this.id},
                    async: false
                });
                var resultData = JSON.parse(htmlobj.responseText);
                for (var i = 0; i < resultData.length; i++) {
                    this.items.push({
                        author: resultData.authorName,
                        body: resultData.commentBody
                    });
                }
            }
            catch (error) {
                console.log(error);
            }
            finally {

            }
        }
    }
</script>
