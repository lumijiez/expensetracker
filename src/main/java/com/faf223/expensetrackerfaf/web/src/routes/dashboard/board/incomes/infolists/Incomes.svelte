<script>
    import ContentIncome from "./ContentIncome.svelte";
    import { incomeData } from "../../../stores.js";
    import { globalStyles } from "../../../styles.js";

    const textToIcon = {
        'Interest': "<svg xmlns=\"http://www.w3.org/2000/svg\" height=\"16\" width=\"20\" viewBox=\"0 0 640 512\"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d=\"M539.7 237.3c3.1-12.3 4.3-24.8 4.3-37.4C544 107.4 468.6 32 376.1 32c-77.2 0-144.6 53-163 127.8-15.3-13.2-34.9-20.5-55.2-20.5-46.3 0-84 37.7-84 84 0 7.4 .9 15 3.1 22.4-42.9 20.2-70.8 63.7-70.8 111.2C6.2 424.8 61.7 480 129.4 480h381.2c67.7 0 123.2-55.2 123.2-123.2 0-56.4-38.9-106-94.1-119.5zM199.9 401.6c0 8.3-7 15.3-15.3 15.3H153.6c-8.3 0-15.3-7-15.3-15.3V290.6c0-8.3 7-15.3 15.3-15.3h30.9c8.3 0 15.3 7 15.3 15.3v110.9zm89.5 0c0 8.3-7 15.3-15.3 15.3h-30.9c-8.3 0-15.3-7-15.3-15.3V270.1c0-8.3 7-15.3 15.3-15.3h30.9c8.3 0 15.3 7 15.3 15.3v131.5zm89.5 0c0 8.3-7 15.3-15.3 15.3h-30.9c-8.3 0-15.3-7-15.3-15.3V238.8c0-8.3 7-15.3 15.3-15.3h30.9c8.3 0 15.3 7 15.3 15.3v162.7zm87 0c0 8.3-7 15.3-15.3 15.3h-28.5c-8.3 0-15.3-7-15.3-15.3V176.9c0-8.6 7-15.6 15.3-15.6h28.5c8.3 0 15.3 7 15.3 15.6v224.6z\"/></svg>",
        'Salary': "<svg xmlns=\"http://www.w3.org/2000/svg\" height=\"16\" width=\"18\" viewBox=\"0 0 576 512\"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d=\"M64 64C28.7 64 0 92.7 0 128V384c0 35.3 28.7 64 64 64H512c35.3 0 64-28.7 64-64V128c0-35.3-28.7-64-64-64H64zm64 320H64V320c35.3 0 64 28.7 64 64zM64 192V128h64c0 35.3-28.7 64-64 64zM448 384c0-35.3 28.7-64 64-64v64H448zm64-192c-35.3 0-64-28.7-64-64h64v64zM288 160a96 96 0 1 1 0 192 96 96 0 1 1 0-192z\"/></svg>",
        'Freelance Income': "<svg xmlns=\"http://www.w3.org/2000/svg\" height=\"16\" width=\"18\" viewBox=\"0 0 576 512\"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d=\"M64 64C28.7 64 0 92.7 0 128V384c0 35.3 28.7 64 64 64H512c35.3 0 64-28.7 64-64V128c0-35.3-28.7-64-64-64H64zm64 320H64V320c35.3 0 64 28.7 64 64zM64 192V128h64c0 35.3-28.7 64-64 64zM448 384c0-35.3 28.7-64 64-64v64H448zm64-192c-35.3 0-64-28.7-64-64h64v64zM288 160a96 96 0 1 1 0 192 96 96 0 1 1 0-192z\"/></svg>",
        'Investment Income': "<svg xmlns=\"http://www.w3.org/2000/svg\" height=\"16\" width=\"20\" viewBox=\"0 0 640 512\"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d=\"M539.7 237.3c3.1-12.3 4.3-24.8 4.3-37.4C544 107.4 468.6 32 376.1 32c-77.2 0-144.6 53-163 127.8-15.3-13.2-34.9-20.5-55.2-20.5-46.3 0-84 37.7-84 84 0 7.4 .9 15 3.1 22.4-42.9 20.2-70.8 63.7-70.8 111.2C6.2 424.8 61.7 480 129.4 480h381.2c67.7 0 123.2-55.2 123.2-123.2 0-56.4-38.9-106-94.1-119.5zM199.9 401.6c0 8.3-7 15.3-15.3 15.3H153.6c-8.3 0-15.3-7-15.3-15.3V290.6c0-8.3 7-15.3 15.3-15.3h30.9c8.3 0 15.3 7 15.3 15.3v110.9zm89.5 0c0 8.3-7 15.3-15.3 15.3h-30.9c-8.3 0-15.3-7-15.3-15.3V270.1c0-8.3 7-15.3 15.3-15.3h30.9c8.3 0 15.3 7 15.3 15.3v131.5zm89.5 0c0 8.3-7 15.3-15.3 15.3h-30.9c-8.3 0-15.3-7-15.3-15.3V238.8c0-8.3 7-15.3 15.3-15.3h30.9c8.3 0 15.3 7 15.3 15.3v162.7zm87 0c0 8.3-7 15.3-15.3 15.3h-28.5c-8.3 0-15.3-7-15.3-15.3V176.9c0-8.6 7-15.6 15.3-15.6h28.5c8.3 0 15.3 7 15.3 15.6v224.6z\"/></svg>",
        'Comission': "<svg xmlns=\"http://www.w3.org/2000/svg\" height=\"16\" width=\"18\" viewBox=\"0 0 576 512\"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d=\"M64 64C28.7 64 0 92.7 0 128v64c0 8.8 7.4 15.7 15.7 18.6C34.5 217.1 48 235 48 256s-13.5 38.9-32.3 45.4C7.4 304.3 0 311.2 0 320v64c0 35.3 28.7 64 64 64H512c35.3 0 64-28.7 64-64V320c0-8.8-7.4-15.7-15.7-18.6C541.5 294.9 528 277 528 256s13.5-38.9 32.3-45.4c8.3-2.9 15.7-9.8 15.7-18.6V128c0-35.3-28.7-64-64-64H64zm64 112l0 160c0 8.8 7.2 16 16 16H432c8.8 0 16-7.2 16-16V176c0-8.8-7.2-16-16-16H144c-8.8 0-16 7.2-16 16zM96 160c0-17.7 14.3-32 32-32H448c17.7 0 32 14.3 32 32V352c0 17.7-14.3 32-32 32H128c-17.7 0-32-14.3-32-32V160z\"/></svg>",
        'Sold Products': "<svg xmlns=\"http://www.w3.org/2000/svg\" height=\"16\" width=\"18\" viewBox=\"0 0 576 512\"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d=\"M64 64C28.7 64 0 92.7 0 128V384c0 35.3 28.7 64 64 64H512c35.3 0 64-28.7 64-64V128c0-35.3-28.7-64-64-64H64zm64 320H64V320c35.3 0 64 28.7 64 64zM64 192V128h64c0 35.3-28.7 64-64 64zM448 384c0-35.3 28.7-64 64-64v64H448zm64-192c-35.3 0-64-28.7-64-64h64v64zM288 160a96 96 0 1 1 0 192 96 96 0 1 1 0-192z\"/></svg>",
        'Gifts': "<svg xmlns=\"http://www.w3.org/2000/svg\" height=\"16\" width=\"16\" viewBox=\"0 0 512 512\"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d=\"M190.5 68.8L225.3 128H224 152c-22.1 0-40-17.9-40-40s17.9-40 40-40h2.2c14.9 0 28.8 7.9 36.3 20.8zM64 88c0 14.4 3.5 28 9.6 40H32c-17.7 0-32 14.3-32 32v64c0 17.7 14.3 32 32 32H480c17.7 0 32-14.3 32-32V160c0-17.7-14.3-32-32-32H438.4c6.1-12 9.6-25.6 9.6-40c0-48.6-39.4-88-88-88h-2.2c-31.9 0-61.5 16.9-77.7 44.4L256 85.5l-24.1-41C215.7 16.9 186.1 0 154.2 0H152C103.4 0 64 39.4 64 88zm336 0c0 22.1-17.9 40-40 40H288h-1.3l34.8-59.2C329.1 55.9 342.9 48 357.8 48H360c22.1 0 40 17.9 40 40zM32 288V464c0 26.5 21.5 48 48 48H224V288H32zM288 512H432c26.5 0 48-21.5 48-48V288H288V512z\"/></svg>",
        'Government Payments': "<svg xmlns=\"http://www.w3.org/2000/svg\" height=\"16\" width=\"18\" viewBox=\"0 0 576 512\"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.--><path d=\"M64 64C28.7 64 0 92.7 0 128V384c0 35.3 28.7 64 64 64H512c35.3 0 64-28.7 64-64V128c0-35.3-28.7-64-64-64H64zm64 320H64V320c35.3 0 64 28.7 64 64zM64 192V128h64c0 35.3-28.7 64-64 64zM448 384c0-35.3 28.7-64 64-64v64H448zm64-192c-35.3 0-64-28.7-64-64h64v64zM288 160a96 96 0 1 1 0 192 96 96 0 1 1 0-192z\"/></svg>"
    };
</script>

<div id="incomeInfo" style="background-color: {$globalStyles.mainColor}">
    <ContentIncome />
    <div id="listContainer" style="color: {$globalStyles.color}">
        <ul>
            {#each $incomeData.reverse() as item}
                <li style="display:flex; justify-content: space-between; color: {$globalStyles.color}">
                    <span>
                    {#if textToIcon[item.incomeCategory.name]}
                        {@html textToIcon[item.incomeCategory.name]}
                    {/if}
                    <span style="font-weight: bold">{item.incomeCategory ? `${item.incomeCategory.name}: ` : `${item.expenseCategory.name}: `}</span>
                    <span style="margin-right: 10px; color: green; font-size: larger">{item.incomeCategory ? `+${item.amount}$` : `-${item.amount}$`}</span>
                    </span>
                    <span style="">{`${item.date}`}</span>
                </li>
            {/each}
        </ul>
    </div>
</div>

<style>

    #incomeInfo {
        min-width: 300px;
        min-height: 0;
        background-color: #212942;
        color: white;
        border-radius: 0 0 10px 10px;
        margin: 0 0 10px 0;
        display: flex;
        flex-direction: column;
        box-sizing: border-box;
    }

    #listContainer {
        flex: 1 1 auto;
        overflow-y: auto;
        min-height: 0;
        padding: 0 10px 10px;
        margin: 0 0 10px;
        box-sizing: border-box;
        border-radius: 0 0 10px 10px;
    }

    ::-webkit-scrollbar {
        width: 10px;
    }

    /* Track */
    ::-webkit-scrollbar-track {
        background: #f1f1f1;
        border-radius: 10px;
    }

    /* Handle */
    ::-webkit-scrollbar-thumb {
        background: #888;
        border-radius: 10px;
    }

    /* Handle on hover */
    ::-webkit-scrollbar-thumb:hover {
        background: #555;
    }

    #listContainer ul {
        list-style: none;
        padding: 0;
        border-radius: 0 0 10px 10px;
    }

    #listContainer li {
        margin-bottom: 20px;
        background-color: #f2f2f2;
        padding: 10px;
        border-radius: 5px;
        box-shadow: 0 1px 3px rgba(0, 0, 0, 0.12), 0 1px 2px rgba(0, 0, 0, 0.24);
        transition: all 0.3s cubic-bezier(.25, .8, .25, 1);
    }

    #listContainer li:hover {
        box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
    }
</style>
