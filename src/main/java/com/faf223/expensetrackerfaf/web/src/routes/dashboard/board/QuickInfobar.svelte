<script>

</script>

<div id="quickInfobar">
    <div class="infobarElement">Income for this month 50$</div>
    <div class="infobarElement">Expense for this month 40$</div>
    <div class="infobarElement">Income for this month 50$</div>
    <div class="infobarElement">Expense for this month 40$</div>
</div>

<style>
    #quickInfobar {
        display: flex;
        justify-content: space-between;
        margin: 20px;
    }

    .infobarElement {
        margin: 10px;
        width: 200px;
        min-width: 100px;
        height: 100px;
        padding: 10px;
        border-radius: 10px;
        background-color: #ffdde2;
        box-shadow: 0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24);
        transition: all 0.3s cubic-bezier(.25,.8,.25,1);
        /* border: 1px solid black; */
    }

    .infobarElement:hover {
        /* color:white; */
        /* background-color: black; */
        box-shadow: 0 14px 28px rgba(0,0,0,0.25), 0 10px 10px rgba(0,0,0,0.22);
    }
</style>