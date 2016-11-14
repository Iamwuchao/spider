#!/bin/bash
echo "# spider" >> README.md
git init
git add README.md
git commit -m "first commit"
git remote add origin https://github.com/Iamwuchao/spider.git
git push -u origin master

