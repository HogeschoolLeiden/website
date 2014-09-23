var collapseDivs, collapseLinks;

window.onload = function (evt) {
    createDocumentStructure('accordian');
}

function createDocumentStructure(className) {
	if (document.getElementsByClassName) {
		var elements = document.getElementsByClassName(className);
		collapseDivs = new Array(elements.length);
		collapseLinks = new Array(elements.length);
		for (var i = 0; i < elements.length; i++) {
			var element = elements[i];
			var siblingContainer;
			if (document.createElement
					&& (siblingContainer = document.createElement('div'))
					&& siblingContainer.style) {
				var nextSibling = element.nextSibling;
				element.parentNode.insertBefore(siblingContainer, nextSibling);
				var nextElement = elements[i + 1];
				while (nextSibling != nextElement && nextSibling != null) {
					var toMove = nextSibling;
					nextSibling = nextSibling.nextSibling;
					siblingContainer.appendChild(toMove);
				}
				siblingContainer.style.display = 'none';

				collapseDivs[i] = siblingContainer;

				createCollapseLink(element, siblingContainer, i);
				
				var clearContainer;
				if (document.createElement
						&& (clearContainer = document.createElement('div'))) {
					createClearDiv(element, clearContainer, i);
				}
			} else {
				// no dynamic creation of elements possible
				return;
			}
		}
	}
}

function createCollapseLink(element, siblingContainer, index) {
	var collapsableDiv;
	
	if (document.createElement && (collapsableDiv = document.createElement('div'))) {
		collapsableDiv.className = "collapsable";		
		collapsableDiv.appendChild(document.createTextNode(String.fromCharCode(160)));
		var link = document.createElement('a');
		link.collapseDiv = siblingContainer;
		link.href = '#';
		link.appendChild(createImg(contextPath + '/images/faq-arrow-right.png','expand','expand'));
		link.onclick = collapseExpandLink;
		collapseLinks[index] = link;
		collapsableDiv.appendChild(link);
		element.appendChild(collapsableDiv);
	}
}

function createImg(src, alt, title) {
    var img = document.createElement('img');
    img.src= src;
    if (alt!=null) img.alt= alt;
    if (title!=null) img.title= title;
    
    return img;
}

function createClearDiv(element, siblingContainer, index) {
	var clearDiv;
	if (document.createElement && (clearDiv = document.createElement('div'))) {
		clearDiv.className = "clear";
		element.appendChild(clearDiv);
	}
}

function collapseExpandLink(evt) {
	if (this.collapseDiv.style.display == '') {
		
		this.parentNode.parentNode.nextSibling.style.display = 'none';		
		this.removeChild(this.firstChild);
		this.appendChild(createImg(contextPath + '/images/faq-arrow-right.png','expand','expand'));

	} else {
		
		this.parentNode.parentNode.nextSibling.style.display = '';
		this.removeChild(this.firstChild);
		this.appendChild(createImg(contextPath + '/images/faq-arrow-down.png','collapse','collapse'));
	}

	if (evt && evt.preventDefault) {
		evt.preventDefault();
	}
	return false;
}

