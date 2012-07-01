'''
Created on Aug 25, 2011
@author: dazhe.meng

plot LD in a certain region
- remade with collections
- remade with transform (trying lower triangular)
'''

import sys
sys.path.append('/home/GMI/dazhe.meng/PyLibrary/')
sys.path.append('/home/GMI/dazhe.meng/Projects/library/trunk/')

from argparse import ArgumentParser
parser = ArgumentParser()
parser.add_argument("-d","--vardata",help="Name of the SNP pickle file",default="testset")
parser.add_argument("-o","--output",help="The name of output figure",default="test1.png")
parser.add_argument("-r","--region",help="Region of interest", default=None)
parser.add_argument("--offsetsize", type=float, help="Ratio to total number of SNPs to use as gap between the axis and ld heatmap", default=0.10)
parser.add_argument("--minr2show", type=float, help="Minimum r2 value to assign a color (rest are grey)", default=0.30)
parser.add_argument("--guidelinedensity", type=int, help="Specify the frequency for drawing guidelines", default=10)
parser.add_argument("--outfile", help="Specify this to save the r2 values to the named file", default="")
args = parser.parse_args()

# env like variable
#data_dir = '/home/GMI/dazhe.meng/Data/Quan2011/'
data_dir = ''

import vardata
import numpy as np
import matplotlib
from matplotlib.transforms import Affine2D
matplotlib.use('Agg')
import matplotlib.pyplot as mpl
from matplotlib.collections import RegularPolyCollection
import matplotlib.cm as cm
from matplotlib.colors import LinearSegmentedColormap
import matplotlib.lines as mpllines
import math

print "Reading SNP data"
S = vardata.read_pickle(args.vardata)

import warnings
warnings.simplefilter("error")

r = map(int, args.region.split(',')) # the r gotten here are global variables!
snp_i_range = S.get_region(r[0],r[1],r[2])

# set some max plot size to prevent possible trouble
if not snp_i_range:
    raise ValueError('No SNP in region: %s'%args.region)
elif snp_i_range[1]-snp_i_range[0]>50000:
    raise ValueError('Too many data points')
num_var = snp_i_range[1]-snp_i_range[0]

# Initialize LD
print 'Calculating LD...'
r_mat = S.data_matrix[snp_i_range[0]:snp_i_range[1]].dot(S.data_matrix[snp_i_range[0]:snp_i_range[1]].T)
r_mat = r_mat/S.num_acc
r2_mat = np.square(r_mat)
if args.outfile != "":
    f_r2 = open(args.outfile,'w')
    for i_row, row in enumerate(r2_mat):
        f_r2.write("%s,"%S.vars[snp_i_range[0]+i_row].pos+",".join(map(str,row))+"\n")
print 'Initializing plot...'

fig = mpl.figure()
ax = fig.add_subplot(111)
ax.set_aspect('equal')
ax.spines['right'].set_visible(False)
ax.spines['bottom'].set_visible(False)
ax.spines['left'].set_visible(False)

padding = float(num_var*args.offsetsize)
tlen = num_var+padding

class coord():
    def __init__(self, len, startpos, endpos):
        self.l = len
        self.s = startpos
        self.e = endpos
    
    def get(self, pos):
        frac = float(pos-self.s)/(self.e-self.s)
        return self.l*frac

# get some coordinate info
CO = coord(num_var,r[1],r[2])

# plot ld heatplot and maf intensity
print 'Plotting r2 values...'
colors = []
coords = []
for i_x in xrange(num_var):
    for i_y in xrange(i_x+1,num_var):
        r2 = r2_mat[i_x,i_y]
        coords.append((i_x,num_var-i_y - 1))
        colors.append(min(1,r2))

cdict = {'red':   ((0.0, 0.6, 0.6),
                   (args.minr2show, 0.6, 1.0),
                   (1.0, 1.0, 1.0)),

         'green': ((0.0, 0.6, 0.6),
                   (args.minr2show,0.6,1.0),
                   (1.0, 0.0, 0.0)),

         'blue':  ((0.0, 0.6, 0.6),
                   (args.minr2show, 0.6, 0.0),
                   (1.0, 0.0, 0.0))
        }
ccm = LinearSegmentedColormap('custom_cm', cdict)

translate_offset = num_var/2
collection = RegularPolyCollection(
    #fig.dpi,
    4, # a square
    rotation=45,
    sizes=(1.414,),
    #facecolors = 'k',
    linewidths = 0,
    cmap = ccm,
    offsets = coords,
    transOffset = Affine2D().scale(1/math.sqrt(2)).rotate_deg_around(0, 0, 45).translate(translate_offset, -translate_offset-padding) + ax.transData,
    )

collection.set_array(np.array(colors))
ax.add_collection(collection)
        
# plot the chromosomal region
#ax.plot([0,tlen],[tlen,0],linewidth=1,color='b')

print "Plotting others..."
# plot linker lines
for iv, var in enumerate(S.vars[snp_i_range[0]:snp_i_range[1]]):
    cx = CO.get(var.pos)
    ax.plot([iv,cx],[-padding,0],linewidth=0.5,color='k' if iv%args.guidelinedensity==0 else 'w')

mpl.xlim((0,num_var))
mpl.ylim((-num_var/math.sqrt(2), padding/2))
#mpl.ylim((0,num_var+padding))

ticklist = []
ticklabels = []
# dynamic labeling of intervals
pos_range = r[2]-r[1]
log10scale = int(math.log10(pos_range))
log10residue = float(pos_range) / math.pow(10,log10scale)
if log10residue < 1.2:
    log10multiplier = 0.2
elif log10residue >=1.2 and log10residue < 2.6:
    log10multiplier = 0.5
elif log10residue >= 2.6 and log10residue < 5.8:
    log10multiplier = 1
else:
    log10multiplier = 2
scale_interval = int(log10multiplier * math.pow(10,log10scale))
if scale_interval>=1000000:
    label_scale_interval = scale_interval / 1000000
    label_scale_alphabet = "Mb"
elif scale_interval>=1000:
    label_scale_interval = scale_interval / 1000
    label_scale_alphabet = "kb"
else:
    label_scale_interval = scale_interval
    label_scale_alphabet = "bp"
for i in xrange(int((r[1]-1)/scale_interval)+1, int((r[2])/scale_interval)+1):
    ticklabels.append(i * label_scale_interval)
    ticklist.append(CO.get(i * scale_interval))

ax.yaxis.set_ticks([])
ax.spines['top'].set_visible(True)
ax.spines['top'].set_position('zero')
ax.spines['top'].set_smart_bounds(True)
ax.xaxis.set_ticks_position('top')
mpl.xticks(ticklist, ticklabels)
for tl in ax.get_xticklines():
    tl.set_marker(mpllines.TICKUP)
mpl.colorbar(collection, shrink=0.75, fraction=0.1)
mpl.title("Chr%s:%s-%s"%(r[0], r[1], r[2]))

fig.savefig(args.output, format='png')
        
# Trial code
#import scipy.stats
#import numpy as np

#x = np.array((1,2,3,4,5))
#y = np.array((1,2,-1,4,5))
#z = np.ma.masked_less(y,0)

#print z
#print scipy.stats.pearsonr(x,z)
