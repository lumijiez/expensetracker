<script>
    import Modal from '../modals/Modal.svelte'
    let showModal;
</script>

<div id="exp">
    <h2>Expenses</h2>
    <div id="openModal" class="plus-button" role="button" tabindex="1" on:click={() => (showModal = true)} on:keydown={() => console.log("keydown")}>
        +
    </div>
    <Modal bind:showModal>
        <h2 slot="header">
            modal
            <small><em>adjective</em> mod·al \ˈmō-dəl\</small>
        </h2>

        <ol class="definition-list">
            <li>of or relating to modality in logic</li>
            <li>
                containing provisions as to the mode of procedure or the manner of taking effect —used of a
                contract or legacy
            </li>
            <li>of or relating to a musical mode</li>
            <li>of or relating to structure as opposed to substance</li>
            <li>
                of, relating to, or constituting a grammatical form or category characteristically indicating
                predication
            </li>
            <li>of or relating to a statistical mode</li>
        </ol>

        <a href="https://www.merriam-webster.com/dictionary/modal">merriam-webster.com</a>
    </Modal>
</div>

<style>
    #exp {
        padding-left: 20px;
        padding-right: 20px;
        display: flex;
        align-items: center;
        justify-content: space-between;
    }

    .plus-button {
        font-size: xx-large;
        background: none;
        cursor: pointer;
        border-radius: 10px;
        transition: background 0.3s ease;
    }

    .plus-button:hover {
        background: rgba(128, 128, 128, 0.5);
    }

    #openModal {
        top: 0;
        background-color: #f2f2f2;
        border-radius: 10px 10px 0 0;
        z-index: 1;
        margin: 0;
    }
</style>
