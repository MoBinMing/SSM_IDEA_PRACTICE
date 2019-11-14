<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-switch/3.3.4/css/bootstrap3/bootstrap-switch.min.css" rel="stylesheet">
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-switch/3.3.4/js/bootstrap-switch.min.js"></script>
<style type="text/css">
    body {
        text-align: center
    }

    .SwitchIcon {
        margin: 200px auto;
    }

    .toggle-button {
        display: none;
    }

    .button-label {
        position: relative;
        display: inline-block;
        width: 80px;
        height: 30px;
        background-color: #ccc;
        box-shadow: #ccc 0px 0px 0px 2px;
        border-radius: 30px;
        overflow: hidden;
    }

    .circle {
        position: absolute;
        top: 0;
        left: 0;
        width: 30px;
        height: 30px;
        border-radius: 50%;
        background-color: #fff;
    }

    .button-label .text {
        line-height: 30px;
        font-size: 18px;
        text-shadow: 0 0 2px #ddd;
    }

    .on {
         color: #fff;
        display: none;
        text-indent: -45px;
    }

    .off {
        color: #fff;
        display: inline-block;
        text-indent: 34px;
    }

    .button-label .circle {
        left: 0;
        transition: all 0.3s;
    }

    .toggle-button:checked + label.button-label .circle {
        left: 50px;
    }

    .toggle-button:checked + label.button-label .on {
        display: inline-block;
    }

    .toggle-button:checked + label.button-label .off {
        display: none;
    }

    .toggle-button:checked + label.button-label {
        background-color: #0a79fa;
    }

    .div {
        height: 20px;
        width: 30px;
        background: #51ccee;
    }

</style>


