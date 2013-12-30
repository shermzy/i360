/*
Copyright (c) 2003-2011, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

CKEDITOR.editorConfig = function( config )
{
/*
 Added by Xuehai 30 May 2011.
 Config to CKEditor, it is used to setting email templates. 
 */
config.toolbar='Simple';
config.toolbar_Simple =
[
    ['Source','-','NewPage','Preview','-','Templates','-'],
    ['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
    ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock','-','TextColor','BGColor'],
    '/',
    ['Styles','Format','Font','FontSize']
];
//not resizable
config.resize_enabled=false;
//for enter, using <br> instead of <p>.
config.enterMode=CKEDITOR.ENTER_BR;
//This setting is important, to avoid transfering special html code. some as <, >
config.htmlEncodeOutput=true;
};
