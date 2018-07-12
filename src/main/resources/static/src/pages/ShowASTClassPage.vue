<template>
    <main-layout>
        <img src="/img/jqqd.gif" style="width: 100%">
    </main-layout>
</template>

<script>
    import MainLayout from '../layouts/Main.vue'
    import activeInfoCard from '../components/activeInfoCard.vue'
    export default {
        components: {
            MainLayout
        },
        data() {
            return {
                items: [],
                host: window.location.host,
                webHost: window.location.host,
            }
        },
        methods: {
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
        },
        beforeMount()
        {

        }

    }
</script>
