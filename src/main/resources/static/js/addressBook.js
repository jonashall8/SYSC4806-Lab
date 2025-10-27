// addressBook.js

console.log("✅ addressBook.js loaded successfully!");




/* ---------------------------
   PRIMARY EVENT LISTENERS
---------------------------- */
document.addEventListener("DOMContentLoaded", () => {

    // Attempt to load address books (for the addAddressBook page)
    // This will run on page load and fill the list
    loadAddressBooks();

    //Progressive Enhancement for "Create Address Book"

    const createBookForm = document.getElementById("addAddressBookForm");

    if (createBookForm) {
        createBookForm.addEventListener("submit", (event) => {
            event.preventDefault(); // Hijack the Part 1 form

            // Uses the correct ID "name"
            const name = document.getElementById("name").value;
            createAddressBook(name); // Call the AJAX (Part 2) function
        });
    }

    // --- Progressive Enhancement for "Add Buddy" ---
    const addBuddyForm = document.getElementById("addBuddyForm");

    if (addBuddyForm) {
        addBuddyForm.addEventListener("submit", (event) => {
            event.preventDefault(); // Hijack the Part 1 form

            const addressBookId = document.getElementById("addressBookId").value;
            const name = document.getElementById("name").value;
            const phoneNumber = document.getElementById("phoneNumber").value;
            const address = document.getElementById("address").value;

            // Call the AJAX (Part 2) function
            addBuddy(addressBookId, name, phoneNumber, address);
        });
    }
});

/* ---------------------------
   Load all Address Books (AJAX)
---------------------------- */
async function loadAddressBooks() {
    try {
        // ✅ FIX: Added /api/ prefix
        const response = await fetch("/api/addressBooks");
        if (!response.ok) throw new Error("Failed to fetch address books");

        const books = await response.json();
        const list = document.getElementById("addressBookList");
        if (!list) return; // Exit if we're not on the addAddressBook page

        list.innerHTML = ""; // Clear list
        books.forEach((book) => {
            const li = document.createElement("li");
            // Use Thymeleaf-style link for consistency
            li.innerHTML = `<a href="/addressBooks/${book.id}/view">${book.name}</a>`;
            list.appendChild(li);
        });
    } catch (error) {
        console.error("Error loading address books:", error);
    }
}

/* ---------------------------
   Create New Address Book
---------------------------- */
async function createAddressBook(name) {
    try {
        //
        const response = await fetch("/api/addressBooks", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ name }),
        });

        if (!response.ok) throw new Error("Failed to create address book");

        const newBook = await response.json();

        // Add the new book to the list dynamically
        const list = document.getElementById("addressBookList");
        if (list) {
            const li = document.createElement("li");
            li.innerHTML = `<a href="/addressBooks/${newBook.id}/view">${newBook.name}</a>`;
            list.appendChild(li);
        }


        document.getElementById("name").value = ""; // Clear the form
    } catch (error) {
        console.error("Error creating address book:", error);
    }
}

/* ---------------------------
   View Address Book (and Buddies)
---------------------------- */
async function viewAddressBook(addressBookId) {
    try {
        const response = await fetch(`/addressBooks/${addressBookId}/buddies`);
        if (!response.ok) throw new Error("Failed to load buddies");
        const buddies = await response.json();

        const container = document.getElementById("addressBookView");
        container.innerHTML = `
      <h2>Address Book #${addressBookId}</h2>
      <ul id="buddyList">
        ${buddies.map(b => `<li>${b.name} - ${b.phoneNumber}</li>`).join("")}
      </ul>
      <h3>Add a Buddy</h3>
      <form id="addBuddyForm">
        <input type="hidden" id="addressBookId" value="${addressBookId}">
        <label>Name:</label>
        <input type="text" id="name" required>
        <label>Phone Number:</label>
        <input type="text" id="phoneNumber" required>
        <label>Address:</label>
        <input type="text" id="address" required>
        <button type="submit">Add Buddy</button>
      </form>
    `;

        // Re-register event listener for new dynamically created form
        const addBuddyForm = document.getElementById("addBuddyForm");
        addBuddyForm.addEventListener("submit", (event) => {
            event.preventDefault();
            const name = document.getElementById("name").value;
            const phoneNumber = document.getElementById("phoneNumber").value;
            const address = document.getElementById("address").value;
            addBuddy(addressBookId, name, phoneNumber, address);
        });
    } catch (error) {
        console.error("Error viewing address book:", error);
    }
}


/* ---------------------------
   Add Buddy to an Address Book (AJAX)
---------------------------- */
async function addBuddy(addressBookId, name, phoneNumber, address) {
    try {
        console.log(`🚀 Adding buddy to AddressBook ${addressBookId}`);

        // ✅ FIX: This is the correct Part 2 logic from your inline script
        const response = await fetch(`/api/addressBooks/${addressBookId}/buddies`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                name: name,
                phoneNumber: phoneNumber,
                address: address
            })
        });

        if (!response.ok) throw new Error(`Failed to add buddy (status ${response.status})`);

        // Add the new buddy to the list dynamically
        const newBuddy = await response.json();
        const list = document.getElementById("buddyList");
        if (list) {
            const li = document.createElement("li");
            li.textContent = `${newBuddy.name} (${newBuddy.phoneNumber}) - ${newBuddy.address}`;
            list.appendChild(li);
        }

        // Reset the form
        document.getElementById("addBuddyForm").reset();
        console.log("✅ Buddy added successfully to list!");

    } catch (error) {
        console.error("❌ Error adding buddy:", error);
    }
}

