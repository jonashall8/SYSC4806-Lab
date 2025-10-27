// addressBook.js

console.log("✅ addressBook.js loaded successfully!");

// Run only after page finishes loading

document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("#addBuddyForm");
    if (!form) {
        console.warn("⚠️ No addBuddyForm found on this page");
        return;
    }

    console.log("✅ Found addBuddyForm");

    form.addEventListener("submit", function (event) {
        event.preventDefault(); // Stop default page reload
        console.log("🟢 Add Buddy form submitted");

        // Collect form data
        const buddy = {
            name: document.querySelector("#name").value,
            phoneNumber: document.querySelector("#phoneNumber").value,
            address: document.querySelector("#address").value,
            addressBookId: document.querySelector("#addressBookId").value
        };

        console.log("📦 Sending:", buddy);

        // Send AJAX request
        fetch("/addressBooks/${addressBookId}/view", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(buddy)
        })
            .then(response => {
                if (!response.ok) throw new Error(`HTTP error! ${response.status}`);
                return response.json();
            })
            .then(data => {
                console.log("✅ Buddy added:", data);
                document.querySelector("#result").innerHTML =
                    `<p>✅ Added ${data.name} (${data.phoneNumber}) successfully!</p>`;
            })
            .catch(err => {
                console.error("❌ Error:", err);
                document.querySelector("#result").innerHTML =
                    `<p style="color:red;">Error: ${err.message}</p>`;
            });
    });
});

document.addEventListener("DOMContentLoaded", () => {
    loadAddressBooks();

    // Register "Create Address Book" form (if present)
    const createBookForm = document.getElementById("createAddressBookForm");
    if (createBookForm) {
        createBookForm.addEventListener("submit", (event) => {
            event.preventDefault();
            const name = document.getElementById("name").value;
            createAddressBook(name);
        });
    }

    // Register "Add Buddy" form (if present)
    const addBuddyForm = document.getElementById("addBuddyForm");
    if (addBuddyForm) {
        addBuddyForm.addEventListener("submit", (event) => {
            event.preventDefault();
            const addressBookId = document.getElementById("addressBookId").value;
            const name = document.getElementById("name").value;
            const phoneNumber = document.getElementById("phoneNumber").value;
            const address = document.getElementById("address").value;

            addBuddy(addressBookId, name, phoneNumber, address);
        });
    }
});

/* ---------------------------
   Load all Address Books
---------------------------- */
async function loadAddressBooks() {
    try {
        const response = await fetch("/addressBooks");
        if (!response.ok) throw new Error("Failed to fetch address books");
        const books = await response.json();

        const list = document.getElementById("addressBookList");
        if (!list) return;

        list.innerHTML = "";
        books.forEach((book) => {
            const li = document.createElement("li");
            li.innerHTML = `
                ${book.name} (ID: ${book.id})
                <button onclick="viewAddressBook(${book.id})">View</button>
            `;
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
        const response = await fetch("/addressBooks", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ name }),
        });

        if (!response.ok) throw new Error("Failed to create address book");
        document.getElementById("addressBookName").value = "";
        await loadAddressBooks(); // refresh list
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

