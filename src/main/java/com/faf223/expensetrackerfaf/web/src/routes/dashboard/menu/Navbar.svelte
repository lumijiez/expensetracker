<script>

</script>

<div id="navbar">
    <div id="profilePic">PROFILEPIC</div>
    <div class="menuItem" on:click={() => console.log("help clicked")}>Help</div>
    <div class="menuItem" on:click={() => console.log("contact clicked")}>Contact</div>
    <div class="menuItem" on:click={() => console.log("settings clicked")}>Settings</div>
    <div class="menuItem" on:click={() => console.log("logout clicked")}>Logout</div>
</div>

<style>
    #navbar {
        padding:0;
        background-color: rgb(33, 41, 66);
        display: flex;
        justify-content: flex-end;
    }

    .menuItem {
        font-family: 'Source Sans Pro', sans-serif;
        color:white;
        display: flex;
        align-items: center;
        padding-left: 20px;
        padding-right: 20px;
        text-align: center;
        height: 50px;
    }

    .menuItem:hover {
        background-color: rgb(45, 60, 90);
    }
    
    #profilePic {
        margin-right:auto;
        display: flex;
        padding-left: 20px;
        padding-right: 20px;
        align-items: center;
        color:white;
        justify-self: left;
    }

    
</style>