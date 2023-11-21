<script>
    import {globalStyles} from "../styles.js";
    import {themeDark} from "../styles.js";
    import {themeDefault} from "../styles.js";

    function theme_dark() {
        $globalStyles = themeDark;
    }

    function theme_default() {
        $globalStyles = themeDefault;
    }
</script>

<div>
    <h1>Settings</h1>
    <button class="button-32" on:click={() => theme_default()}>Select</button>

    <button class="button-32" on:click={() => theme_dark()}>Select</button>

    <button class="button-32" on:click={() => theme_dark()}>Select</button>

    <button class="button-32" on:click={() => theme_dark()}>Select</button>

    <button class="button-32" on:click={() => theme_dark()}>Select</button>
</div>

<style>
    .button-32 {
        background-color: #fff000;
        border-radius: 12px;
        color: #000;
        cursor: pointer;
        font-weight: bold;
        padding: 10px 15px;
        text-align: center;
        transition: 200ms;
        width: 100px;
        box-sizing: border-box;
        border: 0;
        font-size: 16px;
        user-select: none;
        -webkit-user-select: none;
        touch-action: manipulation;
    }

    .button-32:not(:disabled):hover,
    .button-32:not(:disabled):focus {
        outline: 0;
        background: #f4e603;
        box-shadow: 0 0 0 2px rgba(0,0,0,.2), 0 3px 8px 0 rgba(0,0,0,.15);
    }

    .button-32:disabled {
        filter: saturate(0.2) opacity(0.5);
        -webkit-filter: saturate(0.2) opacity(0.5);
        cursor: not-allowed;
    }
</style>