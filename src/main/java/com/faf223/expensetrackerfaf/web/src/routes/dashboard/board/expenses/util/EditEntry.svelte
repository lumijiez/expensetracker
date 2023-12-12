<script>
    import {expenseTypes} from "../../../stores.js";
    import { slide } from 'svelte/transition'
    export let item;
    export let isOn;

    function handleSave() {
        const amount = document.getElementById('amountInput').value;
        const expenseCategory = document.getElementById('expenseCategory').value;

        console.log("tryna save: " + item.expenseId + " " + amount + " " + expenseCategory)
        // saveFunction(item.expenseId, amount, expenseCategory);
    }
</script>

<div style="display: flex; flex-direction: column" transition:slide>
    <span id="textf" style="margin-top: 10px; text-align: center">Edit Entry</span>
    <input type="text" id="amountInput" bind:value={item.amount}>
    <select id="expenseCategory" class="form-control">
        {#each $expenseTypes as expense (expense.id)}
            {#if expense.id !== undefined}
                {#if expense.id === item.expenseCategory.id}
                    <option value={expense.id} selected>{expense.name}</option>
                {:else}
                    <option value={expense.id}>{expense.name}</option>
                {/if}
            {/if}
        {/each}
    </select>
    <div style="margin: 10px; display:flex; flex-direction: row; justify-content: space-evenly">
        <button class="buttonCL" id="saveBtn" on:click={handleSave}>SAVE</button>
        <button class="buttonCL" id="cancelBtn" on:click={() => isOn = false}>CANCEL</button>
    </div>
</div>

<style>

    #textf {
        font-family: "Inter UI","SF Pro Display",-apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,Oxygen,Ubuntu,Cantarell,"Open Sans","Helvetica Neue",sans-serif;
        font-size: 20px;
        font-weight: 500;
    }

    input[type=text] {
        padding: 12px 20px;
        margin: 8px 0;
        display: inline-block;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-sizing: border-box;
    }

    select {
        padding: 12px 20px;
        margin: 8px 0;
        display: inline-block;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-sizing: border-box;
        font-size: 16px;
    }

    #saveBtn {
        background-image: linear-gradient(92.88deg, #455EB5 9.16%, #5643CC 43.89%, #673FD7 64.72%);
        border-radius: 8px;
        border-style: none;
        box-sizing: border-box;
        color: #FFFFFF;
        cursor: pointer;
        flex-shrink: 0;
        font-family: "Inter UI","SF Pro Display",-apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,Oxygen,Ubuntu,Cantarell,"Open Sans","Helvetica Neue",sans-serif;
        font-size: 16px;
        font-weight: 500;
        height: 3rem;
        padding: 0 1.6rem;
        text-align: center;
        text-shadow: rgba(0, 0, 0, 0.25) 0 3px 8px;
        transition: all .5s;
        user-select: none;
        -webkit-user-select: none;
        touch-action: manipulation;
    }

    #saveBtn:hover {
        box-shadow: rgba(80, 63, 205, 0.5) 0 1px 30px;
        transition-duration: .1s;
    }

    #cancelBtn {
        background-image: linear-gradient(92.88deg, #455EB5 9.16%, #5643CC 43.89%, #673FD7 64.72%);
        border-radius: 8px;
        border-style: none;
        box-sizing: border-box;
        color: #FFFFFF;
        cursor: pointer;
        flex-shrink: 0;
        font-family: "Inter UI","SF Pro Display",-apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,Oxygen,Ubuntu,Cantarell,"Open Sans","Helvetica Neue",sans-serif;
        font-size: 16px;
        font-weight: 500;
        height: 3rem;
        padding: 0 1.6rem;
        text-align: center;
        text-shadow: rgba(0, 0, 0, 0.25) 0 3px 8px;
        transition: all .5s;
        user-select: none;
        -webkit-user-select: none;
        touch-action: manipulation;
    }

    #cancelBtn:hover {
        box-shadow: rgba(80, 63, 205, 0.5) 0 1px 30px;
        transition-duration: .1s;
    }
</style>