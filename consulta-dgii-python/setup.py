from setuptools import setup, find_packages

setup(name='consulta-dgii-python',
      version='0.1',
      description='Wrappers para consultas al portal de DGII',
      url='https://github.com/danielgpm/consulta-dgii-python',
      author='Daniel Paniagua Mejia',
      author_email='dpaniagua@gmail.com',
      license='BSD',
      packages=find_packages(),
      install_requires=[
        'beautifulsoup4>=4.3.2',
        'requests>=2.0.1'
                       ],
    #test_suite='tests',       
    classifiers=[
    "Programming Language :: Python",
    "License :: OSI Approved :: BSD License",
    "Development Status :: 4 - Beta",
    'Natural Language :: Spanish',
    "Operating System :: OS Independent",
    "Intended Audience :: Developers",
    'Topic :: Software Development :: Libraries :: Python Modules'         
    ],
    zip_safe=False,    
    )