<script>
    import { onMount } from "svelte";

    onMount(() => {
        // Redirect to /auth/login
        window.location.href = '/auth/login';
    });
</script>