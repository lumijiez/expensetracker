<script>
    import { onMount } from "svelte";
    import axios from "axios";
    import { getCookie } from "svelte-cookie";

    let isLoaded = false;

    onMount(async () => {
        isLoaded = false;
        isLoaded = true;
        const token = getCookie('access_token');

        if (token === '') {
            window.location.href = '/auth/login';
            return;
        }

        const config = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        };

        try {
            const response = await axios.get('https://trackio.online:8081/users', config);
            const userResponse = response.data;
            userData = userResponse;

        } catch (error) {
            console.error(error)
        }
    });
</script>

<div class="adminContainer">
    {#if !isLoaded}
        <div class="loading-container">
            <div class="loading-spinner"></div>
            <div class="loading-text">Loading...</div>
        </div>
    {:else}
        LOL
    {/if}
</div>

<style>

    .adminContainer {
        display: flex;
        height: 100%;
        flex-direction: row;
        justify-content: center;
        align-items: center;
    }

    .loading-container {
        display: flex;
        align-items: center;
        justify-content: center;
        flex-direction: column;
    }

    .loading-spinner {
        border: 8px solid rgba(0, 0, 0, 0.1);
        border-top: 8px solid #3498db;
        border-radius: 50%;
        width: 50px;
        height: 50px;
        animation: spin 1s linear infinite;
    }

    @keyframes spin {
        0% { transform: rotate(0deg); }
        100% { transform: rotate(360deg); }
    }

    .loading-text {
        margin-top: 10px;
    }
</style>
