import numpy as np
import pandas as pd
import plotly.graph_objs as go
import plotly.plotly as py
import plotly.figure_factory as ff
import plotly
from plotly import tools
year = 1946

scl =[[0.0, 'rgb(165,0,38)'], [0.1111111111111111, 'rgb(215,48,39)'], [0.2222222222222222, 'rgb(244,109,67)'],
        [0.3333333333333333, 'rgb(253,174,97)'], [0.4444444444444444, 'rgb(254,224,144)'], [0.5555555555555556, 'rgb(224,243,248)'],
        [0.6666666666666666, 'rgb(171,217,233)'],[0.7777777777777778, 'rgb(116,173,209)'], [0.8888888888888888, 'rgb(69,117,180)'],
        [1.0, 'rgb(49,54,149)']]

df = pd.read_csv("BOM_Yearbooks.csv")
df.State[df.State == 'Kentucky'] = 'KY'
df.State[df.State == 'Virginia'] = 'VA'
df.State[df.State == 'West Virginia'] = 'WV'
df.State[df.State == 'Tennessee'] = 'TN'
print(df)
data_slider = []
for year in df['Year'].unique():
    df_segmented =  df[(df['Year']== year)]

    for col in df_segmented.columns:
        df_segmented[col] = df_segmented[col].astype(str)

    data_each_yr = dict(
                        type='choropleth',
                        locations = df_segmented['State'],
                        z=df_segmented['Total'].astype(float),
                        locationmode='USA-states',
                        colorscale = scl,
                        colorbar= {'title':'# Total'})

    data_slider.append(data_each_yr)

steps = []
for i in range(len(data_slider)):
    step = dict(method='restyle',
                args=['visible', [False] * len(data_slider)],
                label='Year {}'.format(i + 1946))
    step['args'][1][i] = True
    steps.append(step)

sliders = [dict(active=0, pad={"t": 1}, steps=steps)]
layout = dict(title ='Appalachia Coal Production 1946-1962', geo=dict(scope='usa',
                       projection={'type': 'albers usa'}),
              sliders=sliders)

fig = dict(data=data_slider, layout=layout)
plotly.offline.plot(fig)