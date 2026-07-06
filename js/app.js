const fakeNotes = [
    {id: 1, title: "Chemical Bonding Basics", elapsedTime: "2 hours ago",
    preview: "An overview of ionic, covalent and metallic bonds. Key differences in electronegativity and bond formation..."},
    {id: 2, title: "Stoichiometry Overview", elapsedTime: "1 day ago", 
    preview: "Notes on mole concepts, molar mass calculations, and balancing chemical equations with practice examples."}
];

const currentPath = window.location.pathname;

let courses = document.querySelectorAll(".course");

courses.forEach(course => {
    const currentCourse = course.querySelector("a");
    const href = currentCourse.getAttribute("href");

    if(href == currentPath){
            course.classList.add("active");
    }
});

const noteContainer = document.getElementById("notes-container");


function renderNotes(notes){
    // clear notes container
    noteContainer.replaceChildren();

    // loop through fake notes array
    for (const note of notes) {
        // create a new article element
        const newNote = document.createElement('article');

        // create a heading element for title
        const newTitle = document.createElement('h4');
        newTitle.textContent = note.title;

        // create a paragraph element for elapsed time
        const newElapsedTime = document.createElement('p');
        newElapsedTime.textContent = note.elapsedTime;
        newElapsedTime.classList.add("time-elapsed");

        // create a paragraph element for preview content
        const newPreview = document.createElement('p');
        newPreview.textContent = note.preview;

        // give it the note-card class
        newNote.classList.add("note-card")

        // append inner elements to article element
        newNote.append(newTitle, newElapsedTime, newPreview);

        // add it to the notes container
        noteContainer.append(newNote);
    }
}

renderNotes(fakeNotes);

