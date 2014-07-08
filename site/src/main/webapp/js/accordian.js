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
		createCollapseExpandAll(elements[0]);
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
		link.appendChild(document.createTextNode('expand'));
		link.onclick = collapseExpandLink;
		collapseLinks[index] = link;
		collapsableDiv.appendChild(link);
		element.appendChild(collapsableDiv);
	}
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
		this.firstChild.nodeValue = 'expand';
	} else {
		this.parentNode.parentNode.nextSibling.style.display = '';
		this.firstChild.nodeValue = 'collapse';
	}

	if (evt && evt.preventDefault) {
		evt.preventDefault();
	}
	return false;
}

function createCollapseExpandAll(firstElement) {
	var div;
	if (document.createElement && (div = document.createElement('div'))) {
		var link = document.createElement('a');
		
		link.href = '#';
		link.appendChild(document.createTextNode('expand all'));
		link.onclick = expandAll;
		div.appendChild(link);
		div.appendChild(document.createTextNode(' '));
		link = document.createElement('a');
		link.href = '#';
		link.appendChild(document.createTextNode('collapse all'));

		link.onclick = collapseAll;
		div.appendChild(link);
		firstElement.parentNode.insertBefore(div, firstElement);
	}
}

function expandAll(evt) {
	for (var i = 0; i < collapseDivs.length; i++) {
		collapseDivs[i].style.display = '';
		collapseLinks[i].firstChild.nodeValue = 'collapse';
	}

	if (evt && evt.preventDefault) {
		evt.preventDefault();
	}
	return false;
}

function collapseAll(evt) {
	for (var i = 0; i < collapseDivs.length; i++) {
		collapseDivs[i].style.display = 'none';
		collapseLinks[i].firstChild.nodeValue = 'expand';
	}

	if (evt && evt.preventDefault) {
		evt.preventDefault();
	}
	return false;
}