<script>
    import { getContext } from 'svelte';
    import Popup from '../popups/IncomePopup.svelte';
    const { open } = getContext('simple-modal');
    const showSurprise = () => open(Popup, { message: "It's aINCOME!" });
</script>

<div id="inc">
    <h2>Incomes</h2>
    <div id="openModal" role="button"
         tabindex="0"
         on:click={showSurprise}
         on:keydown={() => console.log("keydown")}>
        Log out
    </div>
</div>

<style>
    #inc {
        position: sticky;
    }

    #openModal {
        top: 0;
        position: sticky;
        background-color: #f2f2f2;
        padding: 10px;
        border-radius: 10px 10px 0 0;
        z-index: 1;
        padding: 0 0 10px;
        margin: 0;
    }
</style>