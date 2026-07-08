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

        // create div for edit/delete buttons
        const btnsDiv = document.createElement('div');

        // edit and delete buttons
        const editBtn = document.createElement('button');
        editBtn.classList.add("edit-btn");

        const delBtn = document.createElement('button');
        delBtn.classList.add("del-btn");

        const svgNS = "http://www.w3.org/2000/svg"

        // edit svg 
        const svgEditBtnContainer = document.createElementNS(svgNS, 'svg');

        const editPathElement = document.createElementNS(svgNS, 'path');

        svgEditBtnContainer.setAttribute("viewBox", "0 0 24 24");
        svgEditBtnContainer.setAttribute("fill", "none");
        svgEditBtnContainer.setAttribute("stroke-width", "1.5")
        svgEditBtnContainer.setAttribute("stroke", "currentColor");
        svgEditBtnContainer.setAttribute("class", "size-6");

        editPathElement.setAttribute("stroke-linecap", "round");
        editPathElement.setAttribute("stroke-linejoin", "round");
        editPathElement.setAttribute("d", "m16.862 4.487 1.687-1.688a1.875 1.875 0 1 1 2.652 2.652L10.582 16.07a4.5 4.5 0 0 1-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 0 1 1.13-1.897l8.932-8.931Zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0 1 15.75 21H5.25A2.25 2.25 0 0 1 3 18.75V8.25A2.25 2.25 0 0 1 5.25 6H10");

        // span element for edit button text
        const editSpan = document.createElement('span');
        editSpan.textContent = "Edit";

        svgEditBtnContainer.appendChild(editPathElement);

        editBtn.append(svgEditBtnContainer, editSpan);



        // delete svg
        const svgDelBtnContainer = document.createElementNS(svgNS, 'svg');

        const delPathElement = document.createElementNS(svgNS, 'path');

        svgDelBtnContainer.setAttribute("viewBox", "0 0 24 24");
        svgDelBtnContainer.setAttribute("fill", "none");
        svgDelBtnContainer.setAttribute("stroke-width", "1.5")
        svgDelBtnContainer.setAttribute("stroke", "currentColor");
        svgDelBtnContainer.setAttribute("class", "size-6");

        delPathElement.setAttribute("stroke-linecap", "round");
        delPathElement.setAttribute("stroke-linejoin", "round");
        delPathElement.setAttribute("d", "m14.74 9-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 0 1-2.244 2.077H8.084a2.25 2.25 0 0 1-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 0 0-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 0 1 3.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 0 0-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 0 0-7.5 0");

        // span element for delete button text
        const delSpan = document.createElement('span');
        delSpan.textContent = "Delete";

        svgDelBtnContainer.appendChild(delPathElement);

        delBtn.append(svgDelBtnContainer, delSpan);

        btnsDiv.classList.add("edit-delete");

        btnsDiv.append(editBtn, delBtn);

        // give it the note-card class
        newNote.classList.add("note-card")

        // append inner elements to article element
        newNote.append(newTitle, newElapsedTime, newPreview, btnsDiv);

        // add it to the notes container
        noteContainer.append(newNote);
    }
}

renderNotes(fakeNotes);

