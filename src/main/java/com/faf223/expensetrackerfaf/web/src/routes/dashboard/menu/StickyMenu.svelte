<script>
    import { onMount } from 'svelte';
    import axios from 'axios';
    import {deleteCookie, getCookie} from "svelte-cookie";
    import { slide } from 'svelte/transition'
    import {isAdmin, username} from "../stores.js";

    export let onTabClick;

    let isMenuDown = false;

    function toggleMenu() {
        isMenuDown = !isMenuDown;
    }

    function doNothing() {

    }

</script>

<div id="stickyMenu">
    <div id="stickyButton" tabindex="0" role="button" style="background-color: #191f35; color:white; padding: 10px; display: flex; align-items: center; justify-content: space-around" on:click={toggleMenu} on:keydown={doNothing}>
        <img id="iconImg" src='./../../../src/lib/images/adidas.png' width="90px" alt="icon"/>
        <h3>Menu ▼</h3>
    </div>

    {#if isMenuDown}
        <div id="sideMenu" transition:slide>
            <div on:click={() => onTabClick('profile')} tabindex="0" role="button" class="sideMenuItem" on:keydown={doNothing}>
                <svg class="svgimg" xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 448 512"><!--! Font Awesome Free 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. --><path d="M224 256A128 128 0 1 0 224 0a128 128 0 1 0 0 256zm-45.7 48C79.8 304 0 383.8 0 482.3C0 498.7 13.3 512 29.7 512H418.3c16.4 0 29.7-13.3 29.7-29.7C448 383.8 368.2 304 269.7 304H178.3z"/></svg>
                <span class="sideMenuItemText">Profile</span>
            </div>

                <div on:click={() => onTabClick('expenses')} tabindex="0" role="button" class="sideMenuItem" on:keydown={doNothing}>
                    <svg class="svgimg" xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 576 512"><!--! Font Awesome Free 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. --><path d="M64 64C28.7 64 0 92.7 0 128V384c0 35.3 28.7 64 64 64H512c35.3 0 64-28.7 64-64V128c0-35.3-28.7-64-64-64H64zm64 320H64V320c35.3 0 64 28.7 64 64zM64 192V128h64c0 35.3-28.7 64-64 64zM448 384c0-35.3 28.7-64 64-64v64H448zm64-192c-35.3 0-64-28.7-64-64h64v64zM288 160a96 96 0 1 1 0 192 96 96 0 1 1 0-192z"/></svg>
                    <span class="sideMenuItemText">Spendings</span>
                </div>

                <div on:click={() => onTabClick('incomes')} tabindex="0" role="button" class="sideMenuItem" on:keydown={doNothing}>
                    <svg class="svgimg" xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 576 512"><!--! Font Awesome Free 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. --><path d="M64 64C28.7 64 0 92.7 0 128V384c0 35.3 28.7 64 64 64H512c35.3 0 64-28.7 64-64V128c0-35.3-28.7-64-64-64H64zm64 320H64V320c35.3 0 64 28.7 64 64zM64 192V128h64c0 35.3-28.7 64-64 64zM448 384c0-35.3 28.7-64 64-64v64H448zm64-192c-35.3 0-64-28.7-64-64h64v64zM288 160a96 96 0 1 1 0 192 96 96 0 1 1 0-192z"/></svg>
                    <span class="sideMenuItemText">Revenues</span>
                </div>

                <div on:click={() => onTabClick('statistics')} tabindex="0" role="button" class="sideMenuItem" on:keydown={doNothing}>
                    <svg class="svgimg" xmlns="http://www.w3.org/2000/svg" height="16" width="14" viewBox="0 0 448 512"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d="M160 80c0-26.5 21.5-48 48-48h32c26.5 0 48 21.5 48 48V432c0 26.5-21.5 48-48 48H208c-26.5 0-48-21.5-48-48V80zM0 272c0-26.5 21.5-48 48-48H80c26.5 0 48 21.5 48 48V432c0 26.5-21.5 48-48 48H48c-26.5 0-48-21.5-48-48V272zM368 96h32c26.5 0 48 21.5 48 48V432c0 26.5-21.5 48-48 48H368c-26.5 0-48-21.5-48-48V144c0-26.5 21.5-48 48-48z"/></svg>
                    <span class="sideMenuItemText">Statistics</span>
                </div>

                <div on:click={() => onTabClick('settings')} tabindex="0" role="button" class="sideMenuItem" on:keydown={doNothing}>
                    <svg class="svgimg" xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 512 512"><!--! Font Awesome Free 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. --><path d="M495.9 166.6c3.2 8.7 .5 18.4-6.4 24.6l-43.3 39.4c1.1 8.3 1.7 16.8 1.7 25.4s-.6 17.1-1.7 25.4l43.3 39.4c6.9 6.2 9.6 15.9 6.4 24.6c-4.4 11.9-9.7 23.3-15.8 34.3l-4.7 8.1c-6.6 11-14 21.4-22.1 31.2c-5.9 7.2-15.7 9.6-24.5 6.8l-55.7-17.7c-13.4 10.3-28.2 18.9-44 25.4l-12.5 57.1c-2 9.1-9 16.3-18.2 17.8c-13.8 2.3-28 3.5-42.5 3.5s-28.7-1.2-42.5-3.5c-9.2-1.5-16.2-8.7-18.2-17.8l-12.5-57.1c-15.8-6.5-30.6-15.1-44-25.4L83.1 425.9c-8.8 2.8-18.6 .3-24.5-6.8c-8.1-9.8-15.5-20.2-22.1-31.2l-4.7-8.1c-6.1-11-11.4-22.4-15.8-34.3c-3.2-8.7-.5-18.4 6.4-24.6l43.3-39.4C64.6 273.1 64 264.6 64 256s.6-17.1 1.7-25.4L22.4 191.2c-6.9-6.2-9.6-15.9-6.4-24.6c4.4-11.9 9.7-23.3 15.8-34.3l4.7-8.1c6.6-11 14-21.4 22.1-31.2c5.9-7.2 15.7-9.6 24.5-6.8l55.7 17.7c13.4-10.3 28.2-18.9 44-25.4l12.5-57.1c2-9.1 9-16.3 18.2-17.8C227.3 1.2 241.5 0 256 0s28.7 1.2 42.5 3.5c9.2 1.5 16.2 8.7 18.2 17.8l12.5 57.1c15.8 6.5 30.6 15.1 44 25.4l55.7-17.7c8.8-2.8 18.6-.3 24.5 6.8c8.1 9.8 15.5 20.2 22.1 31.2l4.7 8.1c6.1 11 11.4 22.4 15.8 34.3zM256 336a80 80 0 1 0 0-160 80 80 0 1 0 0 160z"/></svg>
                    <span class="sideMenuItemText">Settings</span>
                </div>

            {#if isAdmin}
                <div on:click={() => onTabClick('admin')} tabindex="0" role="button" class="sideMenuItem" on:keydown={doNothing}>
                    <svg class="svgimg" xmlns="http://www.w3.org/2000/svg" height="16" width="14" viewBox="0 0 448 512"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d="M96 128a128 128 0 1 0 256 0A128 128 0 1 0 96 128zm94.5 200.2l18.6 31L175.8 483.1l-36-146.9c-2-8.1-9.8-13.4-17.9-11.3C51.9 342.4 0 405.8 0 481.3c0 17 13.8 30.7 30.7 30.7H162.5c0 0 0 0 .1 0H168 280h5.5c0 0 0 0 .1 0H417.3c17 0 30.7-13.8 30.7-30.7c0-75.5-51.9-138.9-121.9-156.4c-8.1-2-15.9 3.3-17.9 11.3l-36 146.9L238.9 359.2l18.6-31c6.4-10.7-1.3-24.2-13.7-24.2H224 204.3c-12.4 0-20.1 13.6-13.7 24.2z"/></svg>
                    <span class="sideMenuItemText">Admin Panel</span>
                </div>
            {/if}

            <div id="profileSpace">
                <div id="profileInfo">Hello, {$username}</div>
                <div id="logout" role="button"
                     tabindex="0"
                     on:click={() => {
        deleteCookie('access_token');
        deleteCookie('refresh_token');
        window.location.href = '/auth/login';
    }}
                     on:keydown={e => {
        if (e.key === 'Enter' || e.key === ' ') {
            deleteCookie('access_token');
            deleteCookie('refresh_token');
            window.location.href = '/auth/login';
        }
    }}>
                    Log out
                </div>

            </div>
        </div>
    {/if}
</div>



<style>

    #stickyMenu {
        position:sticky;
        top: 0;
    }

    #sideMenu {
        font-family: 'Source Sans Pro', sans-serif;
        display: flex;
        flex-direction: column;
        align-items: center;
        width: 100%;
        margin:0;
        justify-content: center;
        min-width: 150px;
        background-color: #191f35;
        position:absolute;
    }

    .sideMenuItem {
        min-height: 50px;
        color:white;
        display: flex;
        justify-content: center;
        align-items: center;
        border-radius: 20px;
        cursor: pointer;
    }

    .sideMenuItem:hover {
        background-color: rgb(45, 60, 90);
    }

    .sideMenuItemText {
        padding:10px;
    }

    .svgimg {
        fill:white;
    }

    #iconImg {
        max-width: 150px;
    }

    #profileSpace {
        margin-bottom: 20px;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        color: white;
        font-weight: 900;
        font-size: larger;
    }

    #logout {
        background: none;
        cursor: pointer;
        border-radius: 10px;
        transition: background 0.3s ease;
        padding: 5px;
    }

    #logout:hover {
        background: rgba(128, 128, 128, 0.5);
    }
</style>