<script>

import {globalStyles} from "../../../styles.js";
</script>

<div id="header">
    <div id="dashboardTitleWrapper" style="color: {$globalStyles.dashTextColor}">
        <h5>Hello, welcome to your</h5>
        <h1 id="dashboardTitle">Dashboard</h1>
    </div>

    <div id="icons">
        <div class="headerbtn searchButton">
            <svg style="fill: {$globalStyles.dashTextColor}" xmlns="http://www.w3.org/2000/svg" height="1.3em" viewBox="0 0 512 512"><path d="M416 208c0 45.9-14.9 88.3-40 122.7L502.6 457.4c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L330.7 376c-34.4 25.2-76.8 40-122.7 40C93.1 416 0 322.9 0 208S93.1 0 208 0S416 93.1 416 208zM208 352a144 144 0 1 0 0-288 144 144 0 1 0 0 288z"/></svg>
        </div>
        <div class="headerbtn notificationButton">
            <svg style="fill: {$globalStyles.dashTextColor}" xmlns="http://www.w3.org/2000/svg" height="1.3em" viewBox="0 0 448 512"><path d="M224 0c-17.7 0-32 14.3-32 32V49.9C119.5 61.4 64 124.2 64 200v33.4c0 45.4-15.5 89.5-43.8 124.9L5.3 377c-5.8 7.2-6.9 17.1-2.9 25.4S14.8 416 24 416H424c9.2 0 17.6-5.3 21.6-13.6s2.9-18.2-2.9-25.4l-14.9-18.6C399.5 322.9 384 278.8 384 233.4V200c0-75.8-55.5-138.6-128-150.1V32c0-17.7-14.3-32-32-32zm0 96h8c57.4 0 104 46.6 104 104v33.4c0 47.9 13.9 94.6 39.7 134.6H72.3C98.1 328 112 281.3 112 233.4V200c0-57.4 46.6-104 104-104h8zm64 352H224 160c0 17 6.7 33.3 18.7 45.3s28.3 18.7 45.3 18.7s33.3-6.7 45.3-18.7s18.7-28.3 18.7-45.3z"/></svg>
        </div>
    </div>
</div>

<style>
    
    #header {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }

    #dashboardTitleWrapper {
        display: flex;
        flex-direction: column;
        margin:20px;
        margin-bottom: 0px;
    }

    #dashboardTitleWrapper h5 {
        margin: 0;
    }

    #dashboardTitleWrapper h1 {
        margin-top: 0;
    }

    #icons {
        display: flex;
        margin-right:20px;
    }

    .headerbtn {
        width: 70px;
        height: 70px;
        display: flex;
        justify-content: center;
        align-items: center;
        cursor: pointer;
    }

    .headerbtn:hover::before {
        content: "";
        width: 40px;
        height: 40px;
        background-color: rgb(100, 100, 100, 0.25); 
        border-radius: 50%;
        position: absolute;
    }
</style>