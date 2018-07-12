<template>
    <div class="col-sm-12 col-xs-12 col-md-6">
        <div class="thumbnail" style="text-decoration: none;">
            <!--<img v-bind:src="imgUrl"
             alt="图片信息">-->
            <a class="container img-responsive center-block img-rounded"
               :style="{backgroundImage:'url(' + imgUrl + ')'}"
               style=" background-size: cover; background-position: center; height:400px" v-bind:href="href">
            </a>
            <div style="padding:10px; ">
                <h3 class="text-center ">{{ head }}</h3>

                <div style="white-space: pre-wrap;word-wrap: break-word;">

                </div>
                <blockquote class="blockquote-reverse ">
                    <footer>
                        <a v-bind:href="href" style="text-decoration: none;">点击 <cite
                                title="Source Title">报名</cite></a>
                    </footer>
                </blockquote>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        props: {
            head: String,
            body: String,
            imgUrl: String,
            href: String,
            required: true
        }
    }
</script>