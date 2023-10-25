<script>
    import { getContext } from 'svelte';
    import Popup from './Popup.svelte';
    const { open } = getContext('simple-modal');
    const showSurprise = () => open(Popup, { message: "It's aff modal!" });
</script>

<div id="logout" role="button"
     tabindex="0"
     on:click={showSurprise}
     on:keydown={() => console.log("keydown")}>
    Log out
</div>