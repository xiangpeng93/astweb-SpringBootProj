<template>
    <main-layout>
        <div class="container-fluid " style="padding:0px;height: 100%;">
            <div id="carousel-example-generic" class="carousel slide" data-ride="carousel" style="padding:0px;">
                <!-- Indicators -->
                <ol class="carousel-indicators">
                    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                </ol>

                <!-- Wrapper for slides -->
                <div class="carousel-inner" role="listbox">
                    <div class="item active" style="background-image: url(img/headNew.jpg);height: 500px;">
                        <div class="container">
                            <div class="carousel-caption">
                                <h1>创新与动力</h1>
                                <p>不断创新，才能不断成长。<br><code>Maker is continue make.</code></p>
                                <p><a class="btn btn-lg btn-primary" href="#" role="button">Be Maker</a></p>
                            </div>
                        </div>
                    </div>
                    <div class="item" style="background-image: url(img/headTech.jpg);height: 500px;">
                        <div class="container">
                            <div class="carousel-caption">
                                <h1>科技与技术</h1>
                                <p>技术指向未来，科技成就梦想。<br><code>Engine is not just a work.</code></p>
                                <p><a class="btn btn-lg btn-primary" href="#" role="button">Learn Tech</a></p>
                            </div>
                        </div>
                    </div>
                    <div class="item" style="background-image: url(img/headBook.jpg);height: 500px;">
                        <div class="container">
                            <div class="carousel-caption">
                                <h1>生活与书籍</h1>
                                <p>书籍使人进步，生活更有梦想。<br><code>More information, to be intelligence .</code></p>
                                <p><a class="btn btn-lg btn-primary" href="#" role="button">Get Book</a></p>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Controls -->
                <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>


            <div style="margin-top: 10px" class="container text-center">
                <div class="row">
                    <div class="col-lg-4">
                        <img class="img-circle" src="img/streamHead.jpg" width="140" height="140">
                        <h2>STREAM</h2>
                        <p><br></p>
                        <p><a class="btn btn-default" href="#" role="button">更多相关信息</a></p>
                    </div><!-- /.col-lg-4 -->
                    <div class="col-lg-4">
                        <img class="img-circle" src="img/classHead.jpg" alt="Generic placeholder image" width="140" height="140">
                        <h2>课程</h2>
                        <p><br></p>
                        <p><a class="btn btn-default" href="#" role="button">更多相关信息</a></p>
                    </div><!-- /.col-lg-4 -->
                    <div class="col-lg-4">
                        <img class="img-circle" src="img/resultHead.jpg" alt="Generic placeholder image" width="140" height="140">
                        <h2>作品集</h2>
                        <p><br></p>
                        <p><a class="btn btn-default" href="#" role="button">更多相关信息</a></p>
                    </div><!-- /.col-lg-4 -->
                </div>

            </div>
        </div>
    </main-layout>
</template>

<script>
    import MainLayout from '../layouts/Main.vue'

    export default {
        components: {
            MainLayout
        }
    }
</script>
