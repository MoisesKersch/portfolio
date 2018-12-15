(function(factory) {
  if (typeof define === 'function' && define.amd) {
    // AMD
    define([ 'jquery', 'datatables.net' ], function($) {
      return factory($, window, document);
    });
  } else if (typeof exports === 'object') {
    // CommonJS
    module.exports = function(root, $) {
      if (!root) {
        root = window;
      }

      if (!$ || !$.fn.dataTable) {
        $ = require('datatables.net')(root, $).$;
      }

      return factory($, root, root.document);
    };
  } else {
    // Browser
    factory(jQuery, window, document);
  }
}
    (function($, window, document, undefined) {
      'use strict';
      var DataTable = $.fn.dataTable;

      var _instance = 0;

      /**
       * altEditor provides modal editing of records for Datatables
       * 
       * @class altEditor
       * @constructor
       * @param {object}
       *            oTD DataTables settings object
       * @param {object}
       *            oConfig Configuration object for altEditor
       */
      var altEditor = function(dt, opts) {
        if (!DataTable.versionCheck || !DataTable.versionCheck('1.10.8')) {
          throw ("Warning: altEditor requires DataTables 1.10.8 or greater");
        }

        // User and defaults configuration object
        this.c = $.extend(true, {}, DataTable.defaults.altEditor,
            altEditor.defaults, opts);

        /**
         * @namespace Settings object which contains customisable information
         *            for altEditor instance
         */
        this.s = {
          /** @type {DataTable.Api} DataTables' API instance */
          dt : new DataTable.Api(dt),

          /** @type {String} Unique namespace for events attached to the document */
          namespace : '.altEditor' + (_instance++)
        };

        /**
         * @namespace Common and useful DOM elements for the class instance
         */
        this.dom = {
          /** @type {jQuery} altEditor handle */
          modal : $('<div class="dt-altEditor-handle"/>'),
        };

        /* Constructor logic */
        this._constructor();
      }

      $
          .extend(
              altEditor.prototype,
              {
                /***************************************************************
                 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
                 * Constructor * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
                 */

                /**
                 * Initialise the RowReorder instance
                 * 
                 * @private
                 */
                _constructor : function() {
                  // console.log('altEditor Enabled')
                  var that = this;
                  var dt = this.s.dt;

                  this._setup();

                  dt.on('destroy.altEditor', function() {
                    dt.off('.altEditor');
                    $(dt.table().body()).off(that.s.namespace);
                    $(document.body).off(that.s.namespace);
                  });
                },

                /***************************************************************
                 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
                 * Private methods * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
                 */

                /**
                 * Setup dom and bind button actions
                 * 
                 * @private
                 */
                _setup : function() {
                  // console.log('Setup');

                  var that = this;
                  var dt = this.s.dt;

                  // add modal
                  $('body')
                      .append(
                          '\
		          <div class="modal fade" id="altEditor-modal" tabindex="-1" role="dialog">\
		          <div class="modal-dialog">\
		          <div class="modal-content">\
		          <div class="modal-header">\
		          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>\
		          <h4 class="modal-title"></h4>\
		          </div>\
		          <div class="modal-body">\
		          <p></p>\
		          </div>\
		          <div class="modal-footer">\
		          <button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>\
		          <input type="submit" form="altEditor-form" class="btn btn-primary">Salvar</input>\
		          </div>\
		          </div>\
		          </div>\
		          </div>');
                  $(document).on('click', '#saveButton', function(e) {
                    sendJsonData(that);
                  });
                  $(document).on('click', '#cancelButton', function(e) {
                    undoChanges(that);
                  });

                  // add Edit Button
                  if (this.s.dt.button('edit:name')) {
                    this.s.dt.button('edit:name').action(
                        function(e, dt, node, config) {
                          var rows = dt.rows({
                            selected : true
                          }).count();

                          that._openEditModal();
                        });

                    $(document).on('click', '#editRowBtn', function(e) {
                      if (initValidation(that)) {
                        e.preventDefault();
                        e.stopPropagation();
                        that._editRowData();
                        $("#cancelButton").prop('disabled', false);
                      }
                    });

                  }

                  // add Delete Button
                  if (this.s.dt.button('delete:name')) {
                    this.s.dt.button('delete:name').action(
                        function(e, dt, node, config) {
                          var rows = dt.rows({
                            selected : true
                          }).count();

                          that._openDeleteModal();
                        });

                    $(document).on('click', '#excluirController', function(e) {
                      e.preventDefault();
                      e.stopPropagation();
                      that._deleteRow();
                      $(this).prop('disabled', true);
                      $("#cancelButton").prop('disabled', false);
                    });
                  }

                  // add Add Button
                  if (this.s.dt.button('add:name')) {
                    this.s.dt.button('add:name').action(
                        function(e, dt, node, config) {
                          var rows = dt.rows({
                            selected : true
                          }).count();

                          // Deselect any selected row
                          // Important for match-check
                          dt.row({
                            selected : true
                          }).deselect();

                          that._openAddModal();
                        });
                  }

                  // add Refresh button
                  if (this.s.dt.button('refresh:name')) {
                    this.s.dt.button('refresh:name').action(
                        function(e, dt, node, config) {
							if (dt.ajax && dt.ajax.url()) {
							  dt.ajax.reload();
                              console.log("Datatable reloaded.")
                            }
                        });
                  }

                }, // _setup()

                /**
                 * Emit an event on the DataTable for listeners
                 * 
                 * @param {string}
                 *            name Event name
                 * @param {array}
                 *            args Event arguments
                 * @private
                 */
                _emitEvent : function(name, args) {
                  this.s.dt.iterator('table', function(ctx, i) {
                    $(ctx.nTable).triggerHandler(name + '.dt', args);
                  });
                },

                /**
                 * Open Edit Modal for selected row
                 * 
                 * @private
                 */
                _openEditModal : function() {
                  var that = this;
                  var dt = this.s.dt;
                  var columnDefs = [];

                  // Adding column attributes to object.
                  // Please set the ID as readonly.
                  for (var i in dt.context[0].aoColumns) {
                    var obj = dt.context[0].aoColumns[i];
                    columnDefs[i] = {
                        title : obj.sTitle,
                        name : obj.data ? obj.data : obj.mData,
                        type : (obj.type ? obj.type : 'text'),
                        options : (obj.options ? obj.options : []),
                        msg : (obj.errorMsg ? obj.errorMsg : ''),
                        hoverMsg : (obj.hoverMsg ? obj.hoverMsg : ''),
                        pattern : (obj.pattern ? obj.pattern : '.*'),
                        special : (obj.special ? obj.special : ''),
                        unique : (obj.unique ? obj.unique : false)
                      };
                  }
                  var adata = dt.rows({
                    selected : true
                  });
                  
                  $('#dica-form-modal').openModal();
                  
                  var x = 0;
                  
                  $('#id').val(adata.data()[0][columnDefs[x++].name])
                  $('#titulo').val(adata.data()[0][columnDefs[x++].name])
                  $('#descricao').val(adata.data()[0][columnDefs[x++].name])
                  $('#url').val(adata.data()[0][columnDefs[x++].name])
                  $('#editing').val("true")	
                  Materialize.updateTextFields();
                }, 
          
                /**
                 * Open Delete Modal for selected row
                 * 
                 * @private
                 */
                _openDeleteModal : function() {
                	 var that = this;
                     var dt = this.s.dt;
                     var columnDefs = [];

                     // Adding column attributes to object.
                     // Please set the ID as readonly.
                     for (var i in dt.context[0].aoColumns) {
                       var obj = dt.context[0].aoColumns[i];
                       columnDefs[i] = {
                           title : obj.sTitle,
                           name : obj.data ? obj.data : obj.mData,
                           type : (obj.type ? obj.type : 'text'),
                           options : (obj.options ? obj.options : []),
                           msg : (obj.errorMsg ? obj.errorMsg : ''),
                           hoverMsg : (obj.hoverMsg ? obj.hoverMsg : ''),
                           pattern : (obj.pattern ? obj.pattern : '.*'),
                           special : (obj.special ? obj.special : ''),
                           unique : (obj.unique ? obj.unique : false)
                         };
                     }
                     var adata = dt.rows({
                       selected : true
                     });
                     
                     dicaRemove( adata.data()[0][columnDefs[0].name] )
                },

                _deleteRow : function() {
                  var that = this;
                  var dt = this.s.dt;

                  dt.row({
                    selected : true
                  }).remove();
                  dt.draw();
                },

                /**
                 * Open Add Modal for selected row
                 * 
                 * @private
                 */
                _openAddModal : function() {
                    var that = this;
                    var dt = this.s.dt;
                    var columnDefs = [];

                    // Adding column attributes to object.
                    // Please set the ID as readonly.
                    for (var i in dt.context[0].aoColumns) {
                      var obj = dt.context[0].aoColumns[i];
                      columnDefs[i] = {
                          title : obj.sTitle,
                          name : obj.data ? obj.data : obj.mData,
                          type : (obj.type ? obj.type : 'text'),
                          options : (obj.options ? obj.options : []),
                          msg : (obj.errorMsg ? obj.errorMsg : ''),
                          hoverMsg : (obj.hoverMsg ? obj.hoverMsg : ''),
                          pattern : (obj.pattern ? obj.pattern : '.*'),
                          special : (obj.special ? obj.special : ''),
                          unique : (obj.unique ? obj.unique : false)
                        };
                    }
                    var adata = dt.rows({
                      selected : true
                    });
                	$('#dica-form-modal').openModal();
                	$('#dica-form')[0].reset();
                	$('#id').val("");
                },

                _getExecutionLocationFolder : function() {
                  var fileName = "dataTables.altEditor.js";
                  var scriptList = $("script[src]");
                  var jsFileObject = $.grep(scriptList, function(el) {

                    if (el.src.indexOf(fileName) !== -1) {
                      return el;
                    }
                  });
                  var jsFilePath = jsFileObject[0].src;
                  var jsFileDirectory = jsFilePath.substring(0, jsFilePath
                      .lastIndexOf("/") + 1);
                  return jsFileDirectory;
                }
              }); // extend altEditor.prototype

      /**
       * altEditor version
       * 
       * @static
       * @type String
       */
      altEditor.version = '1.0';

      /**
       * altEditor defaults
       * 
       * @namespace
       */
      altEditor.defaults = {
        /**
         * @type {Boolean} Ask user what they want to do, even for a single
         *       option
         */
        alwaysAsk : false,

        /** @type {string|null} What will trigger a focus */
        focus : null, // focus, click, hover

        /** @type {column-selector} Columns to provide auto fill for */
        columns : '', // all

        /** @type {boolean|null} Update the cells after a drag */
        update : null, // false is editor given, true otherwise

        /** @type {DataTable.Editor} Editor instance for automatic submission */
        editor : null
      };

      /**
       * Classes used by altEditor that are configurable
       * 
       * @namespace
       */
      altEditor.classes = {
        /** @type {String} Class used by the selection button */
        btn : 'btn'
      };

      // Attach a listener to the document which listens for DataTables
      // initialisation
      // events so we can automatically initialise
      $(document).on('preInit.dt.altEditor', function(e, settings, json) {
        if (e.namespace !== 'dt') {
          return;
        }

        var init = settings.oInit.altEditor;
        var defaults = DataTable.defaults.altEditor;

        if (init || defaults) {
          var opts = $.extend({}, init, defaults);

          if (init !== false) {
            new altEditor(settings, opts);
          }
        }
      });

      // Alias for access
      DataTable.altEditor = altEditor;
      return altEditor;
    }));

// Input validation for text-fields
var initValidation = function(tableObj) {
  var dt = tableObj.s.dt;
  var isValid = false;
  var errorcount = 0;
  var matchcount = 0;

  // Looping through all inputs
  $('form[name="altEditor-form"] *').filter(':input').each(
      function(i) {
        // We only want the check text inputs.
        if ($(this).attr("type") === "text") {
          var errorLabel = "#" + $(this).attr("id") + "label";
          var unique = $(this).attr("data-unique");

          // Input validation for port range
          if ($(this).attr("data-special") === "portRange") {
            var ports;
            if ($(this).val().includes(":")) {
              ports = $(this).val().split(":")

              // If port numbers aren't integers, then the "<" doesnt work
              // properly
              var num1 = parseInt(ports[0])
              var num2 = parseInt(ports[1])

              if (num1 < num2) {
                if (ports[0].match($(this).attr("pattern"))
                    && ports[1].match($(this).attr("pattern"))) {
                  $(errorLabel).hide();
                  $(errorLabel).empty();
                } else {
                  $(errorLabel).html($(this).attr("data-errorMsg"));
                  $(errorLabel).show();
                  errorcount++
                }
              } else {
                $(errorLabel).html($(this).attr("data-errorMsg"));
                $(errorLabel).show();
                errorcount++
              }

              // If the port isnt a range
            } else if (!$(this).val().match($(this).attr("pattern"))) {
              $(errorLabel).html($(this).attr("data-errorMsg"));
              $(errorLabel).show();
              errorcount++
            } else {

              // If no error
              $(errorLabel).hide();
              $(errorLabel).empty();
            }

            // All other text-inputs
          } else if ($(this).attr("data-special") != "portRange"
              && !$(this).context.checkValidity()) {
            $(errorLabel).html($(this).attr("data-errorMsg"));
            $(errorLabel).show();
            errorcount++;
          } else {
            $(errorLabel).hide();
            $(errorLabel).empty();
          }

          // Checking for dublicate data in columns with unique attribute
          if ($(this).attr("data-unique") === "true") {
            var input = $(this).val();
            // Looping through an array with all data from the column
            $.each(dt.column(i).data(),
                function(index, value) {
                  // Skipping data from the selected row
                  if (index != dt.cell('.selected', 0).data()) {
                    // If value is not empty and found in column
                    if (input != ""
                        && input.toLowerCase() === value.toLowerCase()) {
                      $(errorLabel).html(
                          "Error: Duplicate data is not allowed.");
                      $(errorLabel).show();
                      matchcount++
                      return false;
                    }
                  }
                });
          }
        }
      });

  // When no errors in input and no matches are found
  if (errorcount == 0 && matchcount == 0) {
    isValid = true;
  }

  return isValid;
}

var undoChanges = function(tableObj) {
  var dt = tableObj.s.dt;

  // Modal creation
  $('#altEditor-modal')
      .on(
          'show.bs.modal',
          function() {
            $('#altEditor-modal').find('.modal-title').html('Cancel changes');
            $('#altEditor-modal').find('.modal-body').html(
                'Are you sure you want to undo unsaved changes?');
            $('#altEditor-modal')
                .find('.modal-footer')
                .html(
                    "<button type='button' class='btn btn-danger' data-dismiss='modal'>Não</button>\
     <button class='btn btn-success' data-dismiss='modal' id='cancelConfirm'>Sim</button>");
          });

  $('#altEditor-modal').modal('show');

  // Reload table from AJAX URL on cancel
  $(document).on('click', '#cancelConfirm', function(e) {
    if (dt.ajax.url())
        dt.ajax.reload();
    $('#cancelButton').attr('disabled', 'disabled')
  });
}

var sendJsonData = function(tableObj) {
  var dt = tableObj.s.dt;

  // Building JSON template
  var jsonDataArray = {};
  var comepleteJsonData = {};
  comepleteJsonData.aaData = [];

  // Container for response from server
  var response = document.getElementById("messages");

  // Adding data from each row to JSON array
  for (var i = 0; i < dt.context[0].aoData.length; i++) {
    jsonDataArray[i] = dt.row(i).data();
  }
  // Adding the JSON array to the comlete JSON template
  comepleteJsonData.aaData.push(jsonDataArray);

  // AJAX call to server
  var jqxhr = $.ajax({
    url : "php/" + dt.context[0].sTableId + ".php",
    type : "POST",
    cache : false,
    data : {
      raw : comepleteJsonData
    }
  }).done(function(data) {
    response.innerHTML = data;
    $("#cancelButton").prop('disabled', 'disabled');

  }).fail(function(error) {
    response.style.color = "red";
    response.innerHTML = "*Errorcode from server: " + error.status;

  });
}

