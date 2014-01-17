# Powered by Python 2.7

# To cancel the modifications performed by the script
# on the current graph, click on the undo button.

# Some useful keyboards shortcuts : 
#   * Ctrl + D : comment selected lines.
#   * Ctrl + Shift + D  : uncomment selected lines.
#   * Ctrl + I : indent selected lines.
#   * Ctrl + Shift + I  : unindent selected lines.
#   * Ctrl + Return  : run script.
#   * Ctrl + F  : find selected text.
#   * Ctrl + R  : replace selected text.
#   * Ctrl + Space  : show auto-completion dialog.

from tulip import *
from random import *
import operator

# the updateVisualization(centerViews = True) function can be called
# during script execution to update the opened views

# the pauseScript() function can be called to pause the script execution.
# To resume the script execution, you will have to click on the "Run script " button.

# the runGraphScript(scriptFile, graph) function can be called to launch another edited script on a tlp.Graph object.
# The scriptFile parameter defines the script name to call (in the form [a-zA-Z0-9_]+.py)

# the main(graph) function must be defined 
# to run the script on the current graph

def main(graph): 
	fromNodeId =  graph.getStringProperty("fromNodeId")
	toNodeId =  graph.getStringProperty("toNodeId")
	viewBorderColor =  graph.getColorProperty("viewBorderColor")
	viewBorderWidth =  graph.getDoubleProperty("viewBorderWidth")
	viewColor =  graph.getColorProperty("viewColor")
	viewFont =  graph.getStringProperty("viewFont")
	viewFontSize =  graph.getIntegerProperty("viewFontSize")
	viewLabel =  graph.getStringProperty("viewLabel")
	viewLabelBorderColor =  graph.getColorProperty("viewLabelBorderColor")
	viewLabelBorderWidth =  graph.getDoubleProperty("viewLabelBorderWidth")
	viewLabelColor =  graph.getColorProperty("viewLabelColor")
	viewLabelPosition =  graph.getIntegerProperty("viewLabelPosition")
	viewLayout =  graph.getLayoutProperty("viewLayout")
	viewMetaGraph =  graph.getGraphProperty("viewMetaGraph")
	viewMetric =  graph.getDoubleProperty("viewMetric")
	viewRotation =  graph.getDoubleProperty("viewRotation")
	viewSelection =  graph.getBooleanProperty("viewSelection")
	viewShape =  graph.getIntegerProperty("viewShape")
	viewSize =  graph.getSizeProperty("viewSize")
	viewSrcAnchorShape =  graph.getIntegerProperty("viewSrcAnchorShape")
	viewSrcAnchorSize =  graph.getSizeProperty("viewSrcAnchorSize")
	viewTexture =  graph.getStringProperty("viewTexture")
	viewTgtAnchorShape =  graph.getIntegerProperty("viewTgtAnchorShape")
	viewTgtAnchorSize =  graph.getSizeProperty("viewTgtAnchorSize")
	weight =  graph.getIntegerProperty("weight")
	nbRelationships = graph.getIntegerProperty("nbRelationships")
	
	inputfile = open('/home/guiiii/git/BIV/Enronvisu/data/EsgeValues-Enron.csv')

	
	weightTable = {}
	
	
	i = 0
	for line in inputfile:
		if(i == 0):
			i = 1
		else:
			f,t,w = line.split("*")
			weightTable[f+t] = w
			#print weightTable[f+t]
			#print f+t
	
	for e in graph.getEdges():
		fr = fromNodeId.getEdgeStringValue(e)
		to = toNodeId.getEdgeStringValue(e)
		separator = "'"
		if(to == "Enron Federal Credit Union@ENRON"):
			key = separator + fr+ separator + separator + " " +to + separator
		else:
			key =  separator + fr+ separator + separator +to + separator
		weight.setEdgeValue(e, int(weightTable[key])) 

	#for e in graph.getEdges():
		#print fromNodeId.getEdgeStringValue(e)
		#print weight.getEdgeStringValue(e)
		#print toNodeId.getEdgeStringValue(e)

	relationMap = {}
	for n in graph.getNodes():
		cpt = 0
		for e in graph.getInOutEdges(n):
			cpt = cpt + 1
		nbRelationships.setNodeValue(n, cpt)
		relationMap[n] = cpt
	
	sorted_Relations = sorted(relationMap.iteritems(), key=operator.itemgetter(1), reverse=True)
	#print sorted_Relations
	class1Size = 5 * graph.numberOfNodes() // 100
	class2Size = 10 * graph.numberOfNodes() // 100
	
	class1 = sorted_Relations[0:class1Size]
	class2 = sorted_Relations[0:class2Size]
	print class1
	
	size = 1024
	pos = size / 2
	
	ypos1 = pos + 500
	ypos2 = pos + 1000
	ypos3 = pos + 1500
	ypos4 = pos + 2000
	ypos5 = pos + 2500

	xpos1 = 10
	xpos2 = 20
	xpos3 = 30
	xpos4 = 50
	xpos5 = 300
	xpos6 = 0
	
	for n in graph.getNodes():
		viewColor.setNodeValue(n, tlp.Color(150, 150, 150))
		x = pos + xpos1
		y = pos
		xpos1 = xpos1 + 10
		viewLayout.setNodeValue(n,tlp.Coord(x,y,0))

	for n in class2:
		viewColor.setNodeValue(n[0], tlp.Color(0, 255, 0))
		viewSize.setNodeValue(n[0], tlp.Vec3f(120.0, 120.0, 120.0))
		x = pos + xpos3
		y = ypos4
		xpos3 = xpos1 + 30
		viewLayout.setNodeValue(n,tlp.Coord(x,y,0))	
	
	for n in class1:
		viewColor.setNodeValue(n[0], tlp.Color(255, 0, 0))
		viewSize.setNodeValue(n[0], tlp.Vec3f(150.0, 150.0, 150.0))
		x = pos + xpos5
		y = ypos5
		xpos5 = xpos5 + 300
		viewLayout.setNodeValue(n,tlp.Coord(x,y,0))		
		
	#graph.delEdges(graph.getEdges(), True)
	updateVisualization(centerViews = True)
		
