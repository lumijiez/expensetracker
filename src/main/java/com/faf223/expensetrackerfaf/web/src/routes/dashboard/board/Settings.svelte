<script>
    import {globalStyles} from "../styles.js";
    import {themeDark} from "../styles.js";
    import {themeDefault} from "../styles.js";
    import {themeColorful} from "../styles.js";

    function theme_dark() {
        $globalStyles = themeDark;
    }

    function theme_default() {
        $globalStyles = themeDefault;
    }

    function theme_colorful() {
        $globalStyles = themeColorful;
    }
</script>

<div style="color: {$globalStyles.dashTextColor}">
    <h1>Settings</h1>
    <div id="buttonContainer">
        <div class="settingEntry">
            <span><svg style="fill: yellow" xmlns="http://www.w3.org/2000/svg" height="16" width="16" viewBox="0 0 512 512"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d="M361.5 1.2c5 2.1 8.6 6.6 9.6 11.9L391 121l107.9 19.8c5.3 1 9.8 4.6 11.9 9.6s1.5 10.7-1.6 15.2L446.9 256l62.3 90.3c3.1 4.5 3.7 10.2 1.6 15.2s-6.6 8.6-11.9 9.6L391 391 371.1 498.9c-1 5.3-4.6 9.8-9.6 11.9s-10.7 1.5-15.2-1.6L256 446.9l-90.3 62.3c-4.5 3.1-10.2 3.7-15.2 1.6s-8.6-6.6-9.6-11.9L121 391 13.1 371.1c-5.3-1-9.8-4.6-11.9-9.6s-1.5-10.7 1.6-15.2L65.1 256 2.8 165.7c-3.1-4.5-3.7-10.2-1.6-15.2s6.6-8.6 11.9-9.6L121 121 140.9 13.1c1-5.3 4.6-9.8 9.6-11.9s10.7-1.5 15.2 1.6L256 65.1 346.3 2.8c4.5-3.1 10.2-3.7 15.2-1.6zM160 256a96 96 0 1 1 192 0 96 96 0 1 1 -192 0zm224 0a128 128 0 1 0 -256 0 128 128 0 1 0 256 0z"/></svg></span>
            <h3 class="settingName">Light Mode</h3>
            <button class="button-32" on:click={() => theme_default()}>Select</button>
        </div>

        <div class="settingEntry">
            <span><svg style="fill:darkblue" xmlns="http://www.w3.org/2000/svg" height="16" width="12" viewBox="0 0 384 512"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d="M223.5 32C100 32 0 132.3 0 256S100 480 223.5 480c60.6 0 115.5-24.2 155.8-63.4c5-4.9 6.3-12.5 3.1-18.7s-10.1-9.7-17-8.5c-9.8 1.7-19.8 2.6-30.1 2.6c-96.9 0-175.5-78.8-175.5-176c0-65.8 36-123.1 89.3-153.3c6.1-3.5 9.2-10.5 7.7-17.3s-7.3-11.9-14.3-12.5c-6.3-.5-12.6-.8-19-.8z"/></svg></span>
            <h3 class="settingName">Dark Mode</h3>
            <button class="button-32" on:click={() => theme_dark()}>Select</button>
        </div>

        <div class="settingEntry">
            <span><svg style="fill:pink" xmlns="http://www.w3.org/2000/svg" height="16" width="10" viewBox="0 0 320 512"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d="M160 0a48 48 0 1 1 0 96 48 48 0 1 1 0-96zM88 384H70.2c-10.9 0-18.6-10.7-15.2-21.1L93.3 248.1 59.4 304.5c-9.1 15.1-28.8 20-43.9 10.9s-20-28.8-10.9-43.9l53.6-89.2c20.3-33.7 56.7-54.3 96-54.3h11.6c39.3 0 75.7 20.6 96 54.3l53.6 89.2c9.1 15.1 4.2 34.8-10.9 43.9s-34.8 4.2-43.9-10.9l-33.9-56.3L265 362.9c3.5 10.4-4.3 21.1-15.2 21.1H232v96c0 17.7-14.3 32-32 32s-32-14.3-32-32V384H152v96c0 17.7-14.3 32-32 32s-32-14.3-32-32V384z"/></svg></span>
            <h3 class="settingName">Pinky Theme</h3>
            <button class="button-32" on:click={() => theme_colorful()}>Select</button>
        </div>
    </div>

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

    #buttonContainer {
        display: flex;
        flex-direction: column;
    }

    .settingEntry {
        display: flex;
        flex-direction: row;
        align-items: center;
    }

    .settingName {
        margin: 20px;
    }
</style>