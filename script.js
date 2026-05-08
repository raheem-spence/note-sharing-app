// Class objects
const classes = [
    {id: "chem", name: "CHEM 101", description: "class about Chemistry"},
    {id: "bio", name: "BIO 101", description: "class about Biology"},
    {id: "physics", name: "PHYSICS 101", description: "class about Physics"},
    {id: "spanish", name: "SPANISH 101", description: "class about Spanish"}
]

let notes = [
    {id: 1, classId: "chem", text: "Today we learned about organic compounds and how they interact with one another"},
    {id: 2, classId: "bio", text: "For this lecture, we discussed anatomy."},
    {id: 3, classId: "chem", text: "We learned about chelation today"},
    {id: 4, classId: "bio", text: "We learned about cell structure of prokaryote/eukaryote, membrane transport, photosynthesism, cellular respiration."},
    {id: 5, classId: "bio", text: "Today's topic was Genetics and Heredity: DNA structure, replication, protein synthesis (transcription/translation), Mendelian genetics, molecular genetics, and gene regulation. "},
    {id: 6, classId: "spanish", text: "Learned about the subjunctive and phrases that trigger the subjunctive. For example, 'para que', 'sin que'."},
    {id: 7, classId: "physics", text: "This class we learned about torque and angular momentum."}
]

// Redirect to classes page
// Select button
const loginBtn = document.getElementById('login-btn');

// Check if element exists, meaning we are on that page
if (loginBtn) {
    // Add listener
    loginBtn.addEventListener('click', event => {
        window.location.href = "classes.html";
    })
}

// CLASSES PAGE
// Select classes container
const classesContainer = document.getElementById('classes-container');

// Check if element exist, meaning we are on that page
if (classesContainer) {
    // Add listener to classes container
    classesContainer.addEventListener('click', event => {
       
        // Get parent card
        const parentCard = event.target.closest('.class-card');

        // Class card id
        const classId = parentCard.id;

        // Navigate to page
        window.location.href = `class.html#${classId}`;
    })
} 



// CLASS PAGE 
// Get specific class by hash (e.g. #chem)
// Remove '#' using substring()
const cleanedHash = window.location.hash.substring(1);
const propVal = String(cleanedHash);

// Create div element for textarea and new note button
const newNoteContainer = document.createElement('div');

// Create textarea element for note
const noteTextarea = document.createElement('textarea');
noteTextarea.cols = 65;
noteTextarea.placeholder = "Take a note..."

// Create button to create note
const createBtn = document.createElement('button');
createBtn.textContent = "Create"

// Create H1 element
const heading = document.createElement('h1');

// Create paragraphe element
const para = document.createElement('p');

// Create notes cotainaer
const notesContainer = document.createElement('div');


renderNotes(propVal);

// Add elements to their css classes
heading.classList.add('class-heading');
para.classList.add('class-para');
notesContainer.classList.add('notesContainer');
newNoteContainer.classList.add('new-note-container');
createBtn.classList.add('create-btn');

newNoteContainer.appendChild(noteTextarea);
newNoteContainer.appendChild(createBtn);

document.body.appendChild(heading);
document.body.appendChild(para);
document.body.appendChild(newNoteContainer);
document.body.appendChild(notesContainer);


// Create note
// Event listener for click of create btn
createBtn.addEventListener('click', event => {
    const newNoteContent = noteTextarea.value;

    // Validate input
    const valid = validateInput(newNoteContent);

    if (!valid) {
        return;
    }

    // Create new note object
    // Object ID
    const objId = Date.now();

    // Class ID
    const clsId = propVal;

    const newNoteObj = {id: objId, classId: clsId, text: newNoteContent};

    // Push new note object to notes array
    notes.push(newNoteObj);

    // Clear input
    noteTextarea.value = "";

    // Render notes
    renderNotes(propVal);
})


// Render function to remove duplicate logic for rendeing notes
function renderNotes(classId) {

    // Clear all notes
    notesContainer.replaceChildren();

    // Get class object
    const clsObj = classes.find(cls => cls.id === classId);

    // Get all note objects for specific class
    const noteObjs = notes.filter(note => note.classId === classId);

    // Create paragraph for each note
    for (const note of noteObjs) {
        const noteDiv = document.createElement('div');
        const noteContent = document.createElement('p');
        const deleteBtn = document.createElement('button');
        deleteBtn.textContent = "Delete";

        // Attach note id to delete button
        deleteBtn.dataset.id = note.id;
  
        noteContent.textContent = note.text;
        noteDiv.appendChild(noteContent);
        noteDiv.appendChild(deleteBtn);


        // Add to appropriate css class
        deleteBtn.classList.add('delete-btn');
        noteDiv.classList.add('note-div');
        noteContent.classList.add('note');
        notesContainer.appendChild(noteDiv);
    }

    // Fill elements with class info
    heading.textContent = clsObj.name;
    para.textContent = clsObj.description;
}


// Function to validate input when creating notes
function validateInput(text) {
    if (text.trim() === "") {
        alert("Invalid Input. Try again.");
        return false;
    } else {
        return true;
    }
}

// Event delegation of Notes Container
notesContainer.addEventListener('click', event => {

    // Delete note
    if (event.target.classList.contains('delete-btn')) {
        // Get note id via delete button id
        const noteId = event.target.dataset.id;

        console.log(noteId);

        // Remove the note object with specific note id
        notes = notes.filter(note => note.id !== Number(noteId));

        renderNotes(propVal);
    }
})